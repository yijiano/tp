package seedu.pill.command;

public class HelpCommand extends Command{
    @Override
    public void execute(String[] tasks, String[] command) {
        System.out.println("Available commands:");
        System.out.println("  help   - Show this help message");
        System.out.println("  add    - Add a new medicine to the inventory");
        System.out.println("  list   - List all medicines in the inventory");
        System.out.println("  edit   - Edit details of a medicine");
        System.out.println("  delete - Remove a medicine from the inventory");
        System.out.println("  exit   - Exit the program");
    }
}
