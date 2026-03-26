package seedu.address.model.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // If no keywords exist for tags, return false
        if (keywords.isEmpty()) {
            return true;
        }

        // Convert the set of tags to a set of tag name strings
        Set<Tag> tags = person.getTags();
        Set<String> tagNames = new HashSet<>();
        for (Tag tag : tags) {
            tagNames.add(tag.tagName.toLowerCase());
        }

        // Do the predicate test
        for (String keyword : keywords) {
            if (tagNames.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
