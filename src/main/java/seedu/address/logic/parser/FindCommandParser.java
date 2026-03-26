package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Clean the input args of whitespaces at the ends
        String trimmedArgs = args.trim();

        // Handle case when there is no args to use
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Get the hashmap of prefixes to their string list
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_TAG, PREFIX_PHONE);

        // If the obtained multimap does not have at least one prefix,
        // or if there is a non-empty preamble, then it is an error
        if (!isOnePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_TAG, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // Verify that for phone number and name, there is only one prefix
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE);

        // Create the predicates
        NameContainsKeywordsPredicate namePredicate =
            new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME));
        TagContainsKeywordsPredicate tagPredicate =
            new TagContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_TAG));
        PhoneContainsKeywordsPredicate phonePredicate =
            new PhoneContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_PHONE));

        // Compose the predicates
        Predicate<Person> compositePredicate = namePredicate.and(tagPredicate).and(phonePredicate);

        // Return the appropriate find command object
        return new FindCommand(compositePredicate);


//        String trimmedArgs = args.trim();
//        if (trimmedArgs.isEmpty()) {
//            throw new ParseException(
//                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
//        }
//
//        String[] nameKeywords = trimmedArgs.split("\\s+");
//
//        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Returns true if at least one of the prefixes is present within the map
     */
    private static boolean isOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
