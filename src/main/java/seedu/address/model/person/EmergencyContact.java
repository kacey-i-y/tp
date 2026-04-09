package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an Emergency Contact for a Person.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmergencyContact(String)}
 */
public class EmergencyContact {

    public static final String MESSAGE_CONSTRAINTS =
            "Emergency contact must include a relationship followed by a valid Singapore phone number. "
                    + "The phone number must be exactly 8 digits and start with 8 or 9, "
                    + "and the full value must be in the format 'Relationship Phone' "
                    + "(e.g. 'Mother 91234567').";

    public static final String MESSAGE_EMPTY_EMERGENCY_CONTACT =
            "Emergency contact cannot be blank.";

    public static final String VALIDATION_REGEX =
            "^([A-Za-z][A-Za-z\\s'/-]*)\\s+([89]\\d{3}\\s?\\d{4})$";

    private static final Pattern VALIDATION_PATTERN = Pattern.compile(VALIDATION_REGEX);

    public final String value;

    /**
     * Constructs an {@code EmergencyContact}.
     *
     * @param emergencyContact A valid emergency contact.
     */
    public EmergencyContact(String emergencyContact) {
        requireNonNull(emergencyContact);
        checkArgument(isValidEmergencyContact(emergencyContact), MESSAGE_CONSTRAINTS);
        value = normalizeEmergencyContact(emergencyContact);
    }

    /**
     * Returns true if a given string is a valid emergency contact.
     */
    public static boolean isValidEmergencyContact(String test) {
        requireNonNull(test);

        Matcher matcher = VALIDATION_PATTERN.matcher(test.trim());
        if (!matcher.matches()) {
            return false;
        }

        String phone = matcher.group(2);
        return Phone.isValidPhone(phone);
    }

    /**
     * Normalizes the emergency contact into a consistent format:
     * "Relationship 91234567"
     */
    private static String normalizeEmergencyContact(String emergencyContact) {
        Matcher matcher = VALIDATION_PATTERN.matcher(emergencyContact.trim());
        matcher.matches();

        String relationship = matcher.group(1).trim().replaceAll("\\s+", " ");
        String phone = new Phone(matcher.group(2)).toString();

        return relationship + " " + phone;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EmergencyContact)) {
            return false;
        }
        EmergencyContact otherEc = (EmergencyContact) other;
        return value.equals(otherEc.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
