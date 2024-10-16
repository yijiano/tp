package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.StringMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command that displays help information about available commands.
 */
public class HelpCommand extends Command {
    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());
    private static final List<String> VALID_COMMANDS = Arrays.asList("help", "add", "delete", "edit", "list", "exit");
    private String commandName;
    private boolean verbose;

    public HelpCommand(String commandName, boolean verbose) {
        this.commandName = commandName;
        this.verbose = verbose;
        logger.setLevel(Level.OFF);
        logger.info("Created HelpCommand for command: " + commandName + " with verbose mode: " + verbose);
    }

    /**
     * Executes the help command by displaying information about available commands.
     * @param itemMap       - The item list to be manipulated by the command.
     * @param storage        -
     * @throws PillException -
     */
    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        assert itemMap != null : "ItemList cannot be null";
        assert storage != null : "Storage cannot be null";
        logger.info("Executing HelpCommand");

        if (commandName == null) {
            showGeneralHelp();
        } else {
            showSpecificHelp(commandName);
        }
    }

    /**
     * Displays general help information for every command.
     */
    private void showGeneralHelp() {
        logger.info("Showing general help information");

        System.out.println("Available commands:");
        System.out.println("  help    - Shows this help message");
        System.out.println("  add     - Adds a new item to the list");
        System.out.println("  delete  - Deletes an item from the list");
        System.out.println("  edit    - Edits an item in the list");
        System.out.println("  list    - Lists all items");
        System.out.println("  exit    - Exits the program");
        System.out.println("Type 'help <command>' for more information on a specific command.");
        System.out.println("Type 'help <command> -v' for verbose output with examples.");
    }

    /**
     * Calls the appropriate method depending on what the user requests help for.
     * @param command - optional user input that determines which help information is displayed to the user.
     */
    private void showSpecificHelp(String command) {
        assert command != null : "Command cannot be null";
        logger.info("Showing specific help for command: " + command);

        switch (command.toLowerCase()) {
        case "help":
            showHelpHelp();
            break;
        case "add":
            showAddHelp();
            break;
        case "delete":
            showDeleteHelp();
            break;
        case "edit":
            showEditHelp();
            break;
        case "list":
            showListHelp();
            break;
        case "exit":
            showExitHelp();
            break;
        default:
            suggestSimilarCommand(command);
        }
    }

    /**
     * Suggests a similar command when an unknown command is entered.
     * @param command - the unknown command entered by the user.
     */
    private void suggestSimilarCommand(String command) {
        logger.info("Suggesting similar command for: " + command);

        String closestMatch = StringMatcher.findClosestMatch(command, VALID_COMMANDS);

        System.out.println("Unknown command: " + command);

        if (closestMatch != null) {
            System.out.println("Did you mean: " + closestMatch + "?");
            System.out.println("Type 'help " + closestMatch + "' for more information on this command.");
        } else {
            System.out.println("No similar command found.");
        }

        System.out.println("\nAvailable commands: " + String.join(", ", VALID_COMMANDS));
        System.out.println("Type 'help <command>' for more information on a specific command.");
    }

    /**
     * Prints detailed information about the 'help' command.
     */
    private void showHelpHelp() {
        logger.fine("Showing help information for 'help' command");

        System.out.println("help: Shows help information about available commands.");
        if (verbose) {
            System.out.println("Usage: help [command] [-v]");
            System.out.println("  [command] - Optional. Specify a command to get detailed help.");
            System.out.println("  [-v]      - Optional. Show verbose output with examples.");
            System.out.println("\nExamples:");
            System.out.println("  help");
            System.out.println("  help add");
            System.out.println("  help add -v");
        }
    }

    /**
     * Prints detailed information about the 'add' command.
     */
    private void showAddHelp() {
        logger.fine("Showing help information for 'add' command");

        System.out.println("add: Adds a new item to the inventory.");
        if (verbose) {
            System.out.println("Usage: add <name> <quantity> <price>");
            System.out.println("  <name>     - Name of the item");
            System.out.println("  <quantity> - Initial quantity of the item");
            System.out.println("\nExample:");
            System.out.println("  add Aspirin 100");
        }
        System.out.println("\nCorrect input format: add <name> <quantity>");
    }

    /**
     * Prints detailed information about the 'delete' command.
     */
    private void showDeleteHelp() {
        logger.fine("Showing help information for 'delete' command");

        System.out.println("delete: Removes an item from the inventory.");
        if (verbose) {
            System.out.println("Usage: delete <name>");
            System.out.println("  <name> - Name of the item to delete (as shown in the list)");
            System.out.println("\nExample:");
            System.out.println("  delete Aspirin");
        }
        System.out.println("\nCorrect input format: delete <name>");
    }

    /**
     * Prints detailed information about the 'edit' command.
     */
    private void showEditHelp() {
        logger.fine("Showing help information for 'edit' command");

        System.out.println("edit: Edits the item in the inventory.");
        if (verbose) {
            System.out.println("Usage: edit <name> <quantity>");
            System.out.println("  <name> - Name of the item to edit (as shown in the list)");
            System.out.println("  <quantity> - New quantity of the item");
            System.out.println("\nExample:");
            System.out.println("  edit Aspirin 100");
        }
        System.out.println("\nCorrect input format: edit <name> <quantity>");
    }

    /**
     * Prints detailed information about the 'list' command.
     */
    private void showListHelp() {
        logger.fine("Showing help information for 'list' command");

        System.out.println("list: Displays all items in the inventory.");
        if (verbose) {
            System.out.println("Usage: list");
            System.out.println("\nExample:");
            System.out.println("  list");
        }
    }

    /**
     * Prints detailed information about the 'exit' command.
     */
    private void showExitHelp() {
        logger.fine("Showing help information for 'exit' command");

        System.out.println("exit: Exits the program.");
        if (verbose) {
            System.out.println("Usage: exit");
            System.out.println("\nExample:");
            System.out.println("  exit");
        }
    }

    /**
     * @return false as this command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
