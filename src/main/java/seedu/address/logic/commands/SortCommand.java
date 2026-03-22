package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the displayed athlete list by the specified field and order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed athlete list by the specified field.\n"
            + "Parameters: by/FIELD [order/ORDER]\n"
            + "Supported fields: name, pb\n"
            + "Supported orders: asc, desc\n"
            + "Example: " + COMMAND_WORD + " by/name order/asc";

    public static final String MESSAGE_SUCCESS = "Sorted athletes by %s in %s order.";

    private final SortField sortField;
    private final SortOrder sortOrder;

    /**
     * Creates a SortCommand to sort the displayed athlete list by the given field and order.
     *
     * @param sortField Field to sort the displayed athlete list by.
     * @param sortOrder Order to sort the displayed athlete list in.
     */
    public SortCommand(SortField sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Comparator<Person> comparator = getComparator();
        model.sortFilteredPersonList(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortField, sortOrder));
    }

    /**
     * Returns a comparator corresponding to the configured sort field and sort order.
     * For personal best sorting, athletes without run timings are placed after athletes
     * with recorded timings regardless of sort direction.
     *
     * @return Comparator used to sort the displayed athlete list.
     * @throws CommandException if the sort field is unsupported.
     */
    private Comparator<Person> getComparator() throws CommandException {
        switch (sortField) {
        case NAME:
            Comparator<Person> nameComparator = Comparator.comparing(
                    person -> person.getName().toString().toLowerCase());
            return sortOrder == SortOrder.DESC ? nameComparator.reversed() : nameComparator;

        case PB:
            if (sortOrder == SortOrder.ASC) {
                return Comparator
                        .comparing((Person person) -> !person.hasRunTimings())
                        .thenComparingDouble(Person::getBestTime);
            } else {
                return Comparator
                        .comparing((Person person) -> !person.hasRunTimings())
                        .thenComparing(Comparator.comparingDouble(Person::getBestTime).reversed());
            }

        default:
            throw new CommandException("Unsupported sort field: " + sortField);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortField.equals(otherSortCommand.sortField)
                && sortOrder.equals(otherSortCommand.sortOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortField", sortField)
                .add("sortOrder", sortOrder)
                .toString();
    }

    /**
     * Represents the supported fields that the displayed athlete list can be sorted by.
     */
    public enum SortField {
        NAME,
        PB;

        @Override
        public String toString() {
            switch (this) {
            case NAME:
                return "name";
            case PB:
                return "personal best";
            default:
                throw new AssertionError("Unknown sort field: " + this);
            }
        }
    }

    /**
     * Represents the supported fields that the displayed athlete list can be sorted by.
     */
    public enum SortOrder {
        ASC,
        DESC;

        @Override
        public String toString() {
            switch (this) {
            case ASC:
                return "ascending";
            case DESC:
                return "descending";
            default:
                throw new AssertionError("Unknown sort order: " + this);
            }
        }
    }
}
