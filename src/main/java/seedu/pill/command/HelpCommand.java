package seedu.pill.command;

import seedu.pill.util.ItemList;

/**
 * Represents a command that displays help information about available commands.
 */
public class HelpCommand extends Command {
    private String commandName;

    public HelpCommand() {
        this.commandName = null;
    }

    public HelpCommand(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Executes the help command by displaying information about available commands.
     *
     * @param itemList The item list (not used in this command).
     */
    @Override
    public void execute(ItemList itemList) {
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
        //System.out.println("  find    - Finds items containing given keywords");
        System.out.println("  list    - Lists all items");
        System.out.println("  quit    - Exits the program");
        System.out.println("Type 'help <command>' for more information on a specific command.");
    }

    /**
     * Shows detailed help information for each command.
     * @param command
     */
    private void showSpecificHelp(String command) {
        switch (command.toLowerCase()) {
            case "help":
                System.out.println("help: Shows help information about available commands.");
                System.out.println("Usage: help [command]");
                System.out.println("  [command] - Optional. Specify a command to get detailed help.");
                break;
            case "add":
                System.out.println("add: Adds a new item to the inventory.");
                System.out.println("Usage: add <name> <quantity> <price>");
                System.out.println("  <name>     - Name of the item");
                System.out.println("  <quantity> - Initial quantity of the item");
                break;
            case "delete":
                System.out.println("delete: Removes an item from the inventory.");
                System.out.println("Usage: delete <index>");
                System.out.println("  <name> - Name of the item to delete (as shown in the list)");
                break;
                /**
            case "find":
                System.out.println("find: Searches for items containing the given keywords.");
                System.out.println("Usage: find <keyword> [keyword2] [keyword3] ...");
                System.out.println("  <keyword> - Keyword to search for in item names");
                break;
                 */
            case "list":
                System.out.println("list: Displays all items in the inventory.");
                System.out.println("Usage: list");
                break;
            case "quit":
                System.out.println("bye: Exits the program.");
                System.out.println("Usage: quit");
                break;
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Type 'help' for a list of available commands.");
        }
    }

    /**
     * @return false as this command does not exit the application.
     */
    @Override
    public boolean isExit() { return false; }
}
