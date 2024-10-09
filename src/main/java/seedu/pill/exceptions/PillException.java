package seedu.pill.exceptions;

public class PillException extends Exception {
    /**
     * Constructor for PillException.
     *
     * @param message The cause of the exception using enum {@link ExceptionMessages}.
     */
    public PillException(ExceptionMessages message) {
        super(message.getMessage());
    }

    /**
     * Prints the exception message.
     *
     * @param e The exception message to be printed.
     */
    public static void printException(PillException e) {
        System.out.println(e.getMessage());
    }
}
