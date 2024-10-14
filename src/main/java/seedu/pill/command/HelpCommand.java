package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;

/**
 * Represents a command that displays help information about available commands.
 */
public class HelpCommand extends Command {
    private String commandName;
    private boolean verbose;

    public HelpCommand() {
        this.commandName = null;
        this.verbose = false;
    }

    public HelpCommand(String commandName) {
        this.commandName = commandName;
        this.verbose = false;
    }

    public HelpCommand(String commandName, boolean verbose) {
        this.commandName = commandName;
        this.verbose = verbose;
    }

    /**
     * Executes the help command by displaying information about available commands.
     * @param itemList       - The item list to be manipulated by the command.
     * @param storage        -
     * @throws PillException -
     */
    @Override
    public void execute(ItemList itemList, Storage storage) throws PillException {
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
        System.out.println("Available commands:");
        System.out.println("  help    - Shows this help message");
        System.out.println("  add     - Adds a new item to the list");
        System.out.println("  delete  - Deletes an item from the list");
        System.out.println("  edit    - Edits quantity of an existing item in the list");
        System.out.println("  list    - Lists all items");
        System.out.println("  quit    - Exits the program");
        System.out.println("Type 'help <command>' for more information on a specific command.");
        System.out.println("Add  '-v' after the command for verbose output with examples.");
    }

    /**
     * Calls the appropriate method depending on what the user requests help for.
     * @param command - optional user input that determines which help information is displayed to the user.
     */
    private void showSpecificHelp(String command) {
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
        case "quit":
            showQuitHelp();
            break;
        default:
            System.out.println("Unknown command: " + command);
            System.out.println("Available commands: help, add, delete, edit, list, quit");
            System.out.println("Type 'help <command>' for more information on a specific command.");
        }
    }

    /**
     * Prints detailed information about the 'help' command.
     */
    private void showHelpHelp() {
        System.out.println("help: Shows help information about available commands.");
        System.out.println("Usage: help [command] [-v]");
        System.out.println("  [command] - Optional. Specify a command to get detailed help.");
        System.out.println("  [-v]      - Optional. Show verbose output with examples.");
        if (verbose) {
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
        System.out.println("add: Adds a new item to the inventory.");
        System.out.println("Usage: add <name> <quantity> <price>");
        System.out.println("  <name>     - Name of the item");
        System.out.println("  <quantity> - Initial quantity of the item");
        if (verbose) {
            System.out.println("\nExample:");
            System.out.println("  add Aspirin 100");
        }
        System.out.println("\nCorrect input format: add <name> <quantity>");
    }

    /**
     * Prints detailed information about the 'delete' command.
     */
    private void showDeleteHelp() {
        System.out.println("delete: Removes an item from the inventory.");
        System.out.println("Usage: delete <name>");
        System.out.println("  <name> - Name of the item to delete (as shown in the list)");
        if (verbose) {
            System.out.println("\nExample:");
            System.out.println("  delete Aspirin");
        }
        System.out.println("\nCorrect input format: delete <index>");
    }

    /**
     * Prints detailed information about the 'edit' command.
     */
    private void showEditHelp() {
        System.out.println("edit: Edits the item in the inventory.");
        System.out.println("Usage: edit <name> <quantity>");
        System.out.println("  <name> - Name of the item to edit (as shown in the list)");
        System.out.println("  <quantity> - New quantity of the item");
        if (verbose) {
            System.out.println("\nExample:");
            System.out.println("  edit Aspirin 100");
        }
        System.out.println("\nCorrect input format: edit <name> <quantity>");
    }

    /**
     * Prints detailed information about the 'list' command.
     */
    private void showListHelp() {
        System.out.println("list: Displays all items in the inventory.");
        System.out.println("Usage: list");
        if (verbose) {
            System.out.println("\nExample:");
            System.out.println("  list");
        }
    }

    /**
     * Prints detailed information about the 'quit' command.
     */
    private void showQuitHelp() {
        System.out.println("quit: Exits the program.");
        System.out.println("Usage: quit");
        if (verbose) {
            System.out.println("\nExample:");
            System.out.println("  quit");
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
