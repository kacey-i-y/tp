package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.RunTiming;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    @Test
    public void execute_sortByPbAscending_usesTwoPointFourKmOnly() throws Exception {
        Person fasterTwoPointFour = createPersonWithTimings("Alpha", new RunTiming("2.4km", 10, 0.0));
        Person slowerTwoPointFour = createPersonWithTimings("Bravo", new RunTiming("2.4km", 11, 0.0));
        Person fasterOtherDistanceOnly = createPersonWithTimings("Charlie", new RunTiming("400m", 0, 50.0));

        ModelStubWithPersons model = new ModelStubWithPersons(fasterOtherDistanceOnly,
                slowerTwoPointFour, fasterTwoPointFour);

        new SortCommand(SortCommand.SortField.PB, SortCommand.SortOrder.ASC).execute(model);

        assertEquals(List.of(fasterTwoPointFour, slowerTwoPointFour, fasterOtherDistanceOnly),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByPbDescending_usesTwoPointFourKmOnly() throws Exception {
        Person fasterTwoPointFour = createPersonWithTimings("Alpha", new RunTiming("2.4km", 10, 0.0));
        Person slowerTwoPointFour = createPersonWithTimings("Bravo", new RunTiming("2.4km", 11, 0.0));
        Person fasterOtherDistanceOnly = createPersonWithTimings("Charlie", new RunTiming("400m", 0, 50.0));

        ModelStubWithPersons model = new ModelStubWithPersons(fasterOtherDistanceOnly,
                fasterTwoPointFour, slowerTwoPointFour);

        new SortCommand(SortCommand.SortField.PB, SortCommand.SortOrder.DESC).execute(model);

        assertEquals(List.of(slowerTwoPointFour, fasterTwoPointFour, fasterOtherDistanceOnly),
                model.getFilteredPersonList());
    }

    private Person createPersonWithTimings(String name, RunTiming... timings) {
        Person person = new PersonBuilder().withName(name).build();
        for (RunTiming timing : timings) {
            person.addRunTiming(timing);
        }
        return person;
    }

    private static class ModelStubWithPersons implements Model {
        private final ObservableList<Person> persons;

        ModelStubWithPersons(Person... persons) {
            this.persons = FXCollections.observableArrayList(persons);
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> comparator) {
            FXCollections.sort(persons, comparator);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
