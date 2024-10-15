package seedu.pill.exceptions;

public enum ExceptionMessages {
    INVALID_COMMAND         ("Invalid command, please try again.\nType the 'help' command for a list of valid commands."),
    SAVE_ERROR              ("Error saving to file, please try again."),
    LOAD_ERROR              ("Error loading saved data"),
    INVALID_LINE_FORMAT     ("File corrupted. Ignoring invalid line format..."),
    INVALID_QUANTITY        ("Quantity is invalid, please try again"),
    INVALID_QUANTITY_FORMAT ("Quantity provided is not a number, please try again."),
    TOO_MANY_ARGUMENTS      ("Too many arguments. Accepted command formats are as follows:" +
            "\n- [command]                      e.g. help" +
            "\n- [command] <name>               e.g. delete JackDaniels" +
            "\n- [command] <name> <quantity>    e.g. add JackDaniels 1231");

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
