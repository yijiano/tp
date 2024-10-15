package seedu.pill.exceptions;

public enum ExceptionMessages {
    INVALID_COMMAND         ("Invalid command, please try again."),
    SAVE_ERROR              ("Error saving to file, please try again."),
    LOAD_ERROR              ("Error loading saved data"),
    INVALID_LINE_FORMAT     ("Invalid line format, file corrupted"),
    INVALID_QUANTITY        ("Quantity is invalid, please try again"),
    INVALID_QUANTITY_FORMAT ("Quantity provided is not a number, please try again.");

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