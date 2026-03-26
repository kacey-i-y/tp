package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class PhoneContainsKeywordsPredicate implements Predicate<Person>  {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return true;
        }
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                person.getPhone().toString(), keyword));
    }
}
