package seedu.pill.command;

import java.util.Scanner;

/**
 * Represents a command that can be executed.
 * Each command is responsible for performing a specific action in the system.
 */
public abstract class Command {
    /**
     * Executes the command with the specified task list, user interface, and storage.
     *
     * @param tasks       The task list to be manipulated by the command.
     * @param ui          The Scanner that takes input from the terminal.
     * @param storagePath The storage path for saving/loading tasks.
     */
    public abstract void execute(String[] tasks, Scanner ui, String storagePath);

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
