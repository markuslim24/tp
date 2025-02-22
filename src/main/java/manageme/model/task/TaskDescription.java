package manageme.model.task;

import static java.util.Objects.requireNonNull;
import static manageme.commons.util.AppUtil.checkArgument;

public class TaskDescription {
    public static final String MESSAGE_CONSTRAINTS = "Description can take any values, and it should not be"
            + " blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code TaskDescription}.
     *
     * @param taskDescription A Task Description.
     */
    public TaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        checkArgument(isValidDescription(taskDescription), MESSAGE_CONSTRAINTS);
        this.value = taskDescription;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && value.equals(((TaskDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
