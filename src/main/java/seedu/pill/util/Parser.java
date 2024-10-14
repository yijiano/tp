package seedu.pill.util;

import seedu.pill.command.AddItemCommand;
import seedu.pill.command.DeleteItemCommand;
import seedu.pill.command.EditItemCommand;
import seedu.pill.command.FindCommand;
import seedu.pill.command.HelpCommand;
import seedu.pill.command.ListCommand;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.util.Objects;

public class Parser {
    private boolean exitFlag = false;
    private final ItemList items;
    private final Storage storage;

    /**
     * Public constructor for Parser.
     */
    public Parser(ItemList items, Storage storage) {
        this.items = items;
        this.storage = storage;
    }

    /**
     * Processes the user's command.
     *
     * @param input The user's input command from the scanner.
     * @throws PillException If command is invalid
     */
    public void parseCommand(String input) throws PillException {
        String[] splitInput = input.split(" ", 3);
        String commandString = splitInput[0].toLowerCase();
        String argument = splitInput.length > 1 ? splitInput[1].toLowerCase() : null;
        String quantityStr = splitInput.length > 2 ? splitInput[2] : "1"; // default quantity is 1

        try {
            switch (commandString) {
            case "exit":
                this.exitFlag = true;
                break;
            case "add":
                new AddItemCommand(argument, parseQuantity(quantityStr)).execute(this.items, this.storage);
                break;
            case "delete":
                new DeleteItemCommand(argument).execute(this.items, this.storage);
                break;
            case "edit":
                new EditItemCommand(argument, parseQuantity(quantityStr)).execute(this.items, this.storage);
                break;
            case "find":
                new FindCommand(argument).execute(this.items, this.storage);
                break;
            case "help":
                boolean flag = quantityStr.equals("-v");
                new HelpCommand(argument, flag).execute(this.items, this.storage);
                break;
            case "list":
                new ListCommand().execute(this.items, this.storage);
                break;
            default:
                throw new PillException(ExceptionMessages.INVALID_COMMAND);
            }
        } catch (PillException e) {
            PillException.printException(e);
        }
    }

    /**
     * Parses the quantity string into an integer.
     *
     * @param quantityStr The string representation of the quantity to parse.
     * @return The parsed quantity as an integer.
     * @throws PillException If the quantity is not a valid positive integer.
     */
    private int parseQuantity(String quantityStr) throws PillException {
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                throw new PillException(ExceptionMessages.INVALID_QUANTITY);
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new PillException(ExceptionMessages.INVALID_QUANTITY_FORMAT);
        }
    }

    /**
     * Returns an exit flag for the Pill bot to exit.
     *
     * @return The state of exit flag.
     */
    public boolean getExitFlag() {
        return this.exitFlag;
    }
}
