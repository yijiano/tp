package seedu.pill.exceptions;

public enum ExceptionMessages {
    INVALID_COMMAND ("Invalid command, please try again.");

    private final String message;

    /**
     * Constructor for ExceptionMessages.
     *
     * @param message The message of the exception.
     */
    ExceptionMessages(String message) {
        this.message = message;
    }

    /**
     * Gets the message of the exception.
     *
     * @return The message of the exception.
     */
    public String getMessage() {
        return this.message;
    }
}
