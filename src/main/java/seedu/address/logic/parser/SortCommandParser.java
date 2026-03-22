package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.SortField;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SortCommand} object.
 */

public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SortCommand}
     * and returns a {@code SortCommand} object for execution.
     *
     * @param args User input arguments.
     * @return A SortCommand containing the parsed sort field and sort order.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_BY, CliSyntax.PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_BY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_BY, PREFIX_ORDER);
        SortField sortField = ParserUtil.parseBy(argMultimap.getValue(PREFIX_BY).get());
        SortOrder sortOrder = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).orElse("asc"));

        return new SortCommand(sortField, sortOrder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
