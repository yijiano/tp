package seedu.pill.util;

import seedu.pill.command.AddItemCommand;
import seedu.pill.command.DeleteItemCommand;
import seedu.pill.command.EditItemCommand;
import seedu.pill.command.FindCommand;
import seedu.pill.command.HelpCommand;
import seedu.pill.command.ListCommand;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    private boolean exitFlag = false;
    private final ItemMap items;
    private final Storage storage;

    /**
     * Public constructor for Parser.
     */
    public Parser(ItemMap items, Storage storage) {
        this.items = items;
        this.storage = storage;
    }

    /**
     * Processes the user's command.
     *
     * @param input The user's input command from the scanner.
     */
    public void parseCommand(String input) {
        try {
            String[] splitInput = input.split("\\s+");
            if (splitInput.length > 3) {
                throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
            }
            assert(splitInput.length <= 3);
            String commandString = splitInput[0].toLowerCase();
            String argument = splitInput.length > 1 ? splitInput[1].toLowerCase() : null;
            String quantityStr = splitInput.length > 2 ? splitInput[2] : "1"; // default quantity is 1

            switch (commandString) {
            case "exit":
                this.exitFlag = true;
                break;
            case "add":
                parseAddItemCommand(splitInput).execute(this.items, this.storage);
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
     * Parses the input and creates an {@code AddItemCommand} object.
     * The input must be an array of strings that represents the item name, quantity, and optional expiry date.
     * If the input exceeds four elements, a {@code PillException} is thrown.
     *
     * @param splitInput An array of strings representing the user's input.
     *                   The array can contain the item name, quantity, and expiry date (optional).
     * @return An {@code AddItemCommand} object containing the parsed item name, quantity, and expiry date.
     * @throws PillException If the input contains more than four arguments.
     */
    private AddItemCommand parseAddItemCommand(String[] splitInput) throws PillException {
        if (splitInput.length > 4) {
            throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
        }
        assert(splitInput.length <= 4);
        String argument = splitInput.length > 1 ? splitInput[1].toLowerCase() : null;
        String quantityStr = "1";
        String expiryDateStr = null;

        if (splitInput.length > 2) {
            if (isANumber(splitInput[2])) {
                quantityStr = splitInput[2];
                if (splitInput.length > 3) {
                    expiryDateStr = splitInput[3];
                }
            } else {
                expiryDateStr = splitInput[2];
            }
        }

        return new AddItemCommand(argument, parseQuantity(quantityStr), parseExpiryDate(expiryDateStr));
    }

    /**
     * Parses a string representing an expiry date into a {@code LocalDate} object.
     *
     * @param expiryDateStr A string representing the expiry date in ISO-8601 format (yyyy-MM-dd).
     * @return A {@code LocalDate} object representing the expiry date, or {@code null} if no expiry date is provided.
     * @throws PillException If the expiry date string is not in the correct format.
     */
    private LocalDate parseExpiryDate(String expiryDateStr) throws PillException {
        try {
            return LocalDate.parse(expiryDateStr);
        } catch (DateTimeParseException e) {
            throw new PillException(ExceptionMessages.PARSE_DATE_ERROR);
        }
    }

    /**
     * Checks if a given string is a valid integer number.
     *
     * @param s The string to check.
     * @return {@code true} if the string can be parsed into an integer; {@code false} otherwise.
     */
    private boolean isANumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
            assert quantity > 0 : "Quantity must be positive";
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
