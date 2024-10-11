package seedu.pill.command;

import seedu.pill.util.ItemList;

/**
 * Represents a command that can be executed.
 * Each command is responsible for performing a specific action in the system.
 */
public abstract class Command {

    /**
     * Executes the command with the specified item list.
     *
     * @param itemList The item list to be manipulated by the command.
     */
    public abstract void execute(ItemList itemList);

    /**
     * Determines whether this command will exit the application.
     * Overridden only by the ByeCommand.
     *
     * @return false, as most commands do not exit the application.
     */
    public boolean isExit() {
        return false;
    }
}
