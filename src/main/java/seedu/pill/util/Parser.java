package seedu.pill.util;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

public class Parser {

    /**
     * Public constructor for Parser.
     */
    public Parser() {
        // TODO: Add taskList init here
    }

    /**
     * Processes the user's command.
     *
     * @param input The user's input command from the scanner.
     * @throws PillException If command is invalid
     */
    public static void parseCommand(String input) throws PillException {
        String[] splitInput = input.split(" ", 2);
        String commandString = splitInput[0].toLowerCase();

        try {
            switch (commandString) {
            case "help":
                // TODO: Add "help" command
                break;
            case "add":
                // TODO: Add "add" command
                break;
            case "list":
                // TODO: Add "list" command
                break;
            case "edit":
                // TODO: Add "edit" command
                break;
            case "delete":
                // TODO: Add "delete" command
                break;
            default:
                throw new PillException(ExceptionMessages.INVALID_COMMAND);
            }
        } catch (PillException e) {
            PillException.printException(e);
        }
    }
}
