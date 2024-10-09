package seedu.pill.command;

/**
 * Represents a command that can be executed.
 * Each command is responsible for performing a specific action in the system.
 */
public abstract class Command {
    /**
     * Executes the command with the specified task list, user interface, and storage.
     *
     * @param tasks   The task list to be manipulated by the command.
     * @param command The command type and the input parameters.
     */
    public abstract void execute(String[] tasks, String[] command);

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
