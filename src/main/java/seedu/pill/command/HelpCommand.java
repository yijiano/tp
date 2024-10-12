package seedu.pill.command;

import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;

/**
 * Represents a command that displays help information about available commands.
 */
public class HelpCommand extends Command {

    /**
     * Executes the help command by displaying information about available commands.
     *
     * @param itemList The item list (not used in this command).
     */
    @Override
    public void execute(ItemList itemList, Storage storage) {
        System.out.println("Available commands:");
        System.out.println("  help    - Shows this help message");
        System.out.println("  add     - Adds a new item to the list");
        System.out.println("  list    - Lists all items");
        System.out.println("  delete  - Deletes an item from the list");
        System.out.println("  find    - Finds items containing given keywords");
        System.out.println("  bye     - Exits the program");
        System.out.println("Type 'help <command>' for more information on a specific command.");
    }

    /**
     * @return false as this command does not exit the application.
     */
    @Override
    public boolean isExit() { return false; }
}
