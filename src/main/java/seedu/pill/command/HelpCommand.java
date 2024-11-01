package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.PillLogger;
import seedu.pill.util.Storage;
import seedu.pill.util.StringMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class HelpCommand extends Command {
    private static final Logger logger = PillLogger.getLogger();
    private static final List<String> VALID_COMMANDS = Arrays.asList(
            "help", "add", "delete", "edit", "expired", "expiring",
            "list", "order", "cancel-order", "fulfill-order", "list-orders",
            "stock-check", "transaction-history", "exit", "cost", "price", "restock", "restockall"
    );

    private final String commandName;
    private final boolean verbose;

    public HelpCommand(String commandInput, boolean verbose) {
        if (commandInput != null) {
            String[] parts = commandInput.split("\\s+", 2);
            this.commandName = parts.length > 0 ? parts[0].toLowerCase() : null;
        } else {
            this.commandName = null;
        }
        this.verbose = verbose;
        logger.info("Created HelpCommand for command: " + commandName + " with verbose mode: " + verbose);
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        if (commandName == null || commandName.isEmpty()) {
            showGeneralHelp();
        } else {
            showSpecificHelp(commandName);
        }
    }

    private void showGeneralHelp() {
        System.out.println("Available commands:");

        System.out.println("\nItem Management:");
        System.out.println("  add           - Adds a new item to the list");
        System.out.println("  delete        - Deletes an item from the list");
        System.out.println("  edit          - Edits an item in the list");
        System.out.println("  expired       - Lists all items that have expired");
        System.out.println("  expiring      - Lists items expiring before a specified date");
        System.out.println("  list          - Lists all items");
        System.out.println("  stock-check   - Lists all items that need to be restocked");
        System.out.println("  restock       - Restocks a specified item with an optional expiry date and quantity");
        System.out.println("  restockall    - Restocks all items below a specified threshold");

        System.out.println("\nPrice and Cost Management:");
        System.out.println("  cost          - Sets the cost for a specified item");
        System.out.println("  price         - Sets the selling price for a specified item");

        System.out.println("\nOrder Management:");
        System.out.println("  order         - Creates a new purchase or dispense order");
        System.out.println("  fulfill-order - Processes and completes a pending order");
        System.out.println("  cancel-order  - Cancels a pending order");
        System.out.println("  list-orders   - Lists all orders with optional filtering");

        System.out.println("\nTransaction Management:");
        System.out.println("  transaction-history - Views transaction history with optional filtering");

        System.out.println("\nOther Commands:");
        System.out.println("  help          - Shows this help message");
        System.out.println("  exit          - Exits the program");

        System.out.println("\nType 'help <command>' for more information on a specific command.");
        System.out.println("Type 'help <command> -v' for verbose output with examples.");
    }

    /**
     * Displays help information for a specific command.
     * If the command is not recognized, attempts to find and suggest similar commands.
     *
     * @param command - The name of the command to show help for.
     */
    private void showSpecificHelp(String command) {
        assert command != null : "Command cannot be null";
        logger.info("Showing specific help for command: " + command);

        switch (command.toLowerCase()) {
        case "restock":
            showRestockHelp();
            break;
        case "restockall":
            showRestockAllHelp();
            break;
        case "cost":
            showCostHelp();
            break;
        case "price":
            showPriceHelp();
            break;
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
        case "expired":
            showExpiredHelp();
            break;
        case "expiring":
            showExpiringHelp();
            break;
        case "list":
            showListHelp();
            break;
        case "order":
            showOrderHelp();
            break;
        case "cancel-order":
            showCancelOrderHelp();
            break;
        case "fulfill-order":
            showFulfillOrderHelp();
            break;
        case "list-orders":
            showListOrdersHelp();
            break;
        case "transaction-history":
            showTransactionHistoryHelp();
            break;
        case "stock-check":
            showStockCheckHelp();
            break;
        case "exit":
            showExitHelp();
            break;
        default:
            String closestMatch = StringMatcher.findClosestMatch(command, VALID_COMMANDS);
            if (closestMatch != null) {
                System.out.println("Did you mean: " + closestMatch + "?");
                showSpecificHelp(closestMatch);
            } else {
                suggestSimilarCommand(command);
            }
        }
    }

    /**
     * Suggests similar commands when an unknown command is entered.
     * Uses string matching to find the closest matching valid command.
     *
     * @param command - The unknown command entered by the user.
     */
    private void suggestSimilarCommand(String command) {
        logger.info("Suggesting similar command for: " + command);

        System.out.println("Unknown command: " + command);
        String closestMatch = StringMatcher.findClosestMatch(command, VALID_COMMANDS);
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
     * Prints detailed information about the 'restock' command.
     */
    private void showRestockHelp() {
        System.out.println("restock: Restocks a specified item with an optional expiry date and quantity.");
        if (verbose) {
            System.out.println("Usage: restock <item-name> [expiry-date] <quantity>");
            System.out.println("  <item-name>    - The name of the item to restock.");
            System.out.println("  [expiry-date]  - Optional. The expiry date of the item in yyyy-MM-dd format.");
            System.out.println("  <quantity>     - The quantity to restock up to.");
            System.out.println("\nExamples:");
            System.out.println("  restock apple 100");
            System.out.println("  restock orange 2025-12-12 50");
        }
    }

    /**
     * Prints detailed information about the 'restockall' command.
     */
    private void showRestockAllHelp() {
        System.out.println("restockall: Restocks all items below a specified threshold.");
        if (verbose) {
            System.out.println("Usage: restockall [threshold]");
            System.out.println("  [threshold] - Optional. The minimum quantity for restocking. Defaults to 50.");
            System.out.println("\nExample:");
            System.out.println("  restockall 100");
        }
    }

    /**
     * Prints detailed information about the 'cost' command.
     */
    private void showCostHelp() {
        System.out.println("cost: Sets the cost for a specified item.");
        if (verbose) {
            System.out.println("Usage: cost <item-name> <cost>");
            System.out.println("  <item-name> - The name of the item.");
            System.out.println("  <cost>      - The cost value to set for the item.");
            System.out.println("\nExample:");
            System.out.println("  cost apple 20.0");
        }
    }

    /**
     * Prints detailed information about the 'price' command.
     */
    private void showPriceHelp() {
        System.out.println("price: Sets the selling price for a specified item.");
        if (verbose) {
            System.out.println("Usage: price <item-name> <price>");
            System.out.println("  <item-name> - The name of the item.");
            System.out.println("  <price>     - The price value to set for the item.");
            System.out.println("\nExample:");
            System.out.println("  price apple 30.0");
        }
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
            System.out.println("Usage: add <name> <quantity> <expiry>");
            System.out.println("  <name>     - Name of the item");
            System.out.println("  <quantity> - Initial quantity of the item");
            System.out.println("  <expiry>   - Expiry date of the item in yyyy-MM-dd format");
            System.out.println("\nExample:");
            System.out.println("  add Aspirin 100 2024-05-24");
        }
        System.out.println("\nCorrect input format: add <name> <quantity> <expiry>");
    }

    /**
     * Prints detailed information about the 'delete' command.
     */
    private void showDeleteHelp() {
        logger.fine("Showing help information for 'delete' command");

        System.out.println("delete: Removes an item from the inventory.");
        if (verbose) {
            System.out.println("Usage: delete <name> <expiry>");
            System.out.println("  <name> - Name of the item to delete (as shown in the list)");
            System.out.println("  <expiry>   - Expiry date of the item in yyyy/MM/dd format.");
            System.out.println("\nExample:");
            System.out.println("  delete Aspirin 2024-05-24");
        }
        System.out.println("\nCorrect input format: delete <name> <expiry>");
    }

    /**
     * Prints detailed information about the 'edit' command.
     */
    private void showEditHelp() {
        logger.fine("Showing help information for 'edit' command");

        System.out.println("edit: Edits the item in the inventory.");
        if (verbose) {
            System.out.println("Usage: edit <name> <quantity> <expiry>");
            System.out.println("  <name> - Name of the item to edit (as shown in the list)");
            System.out.println("  <quantity> - New quantity of the item");
            System.out.println("  <expiry>   - Expiry date of the item in yyyy-MM-dd format");
            System.out.println("\nExample:");
            System.out.println("  edit Aspirin 100 2024-05-24");
        }
        System.out.println("\nCorrect input format: edit <name> <quantity> <expiry>");
    }

    /**
     * Prints detailed information about the 'expired' command.
     */
    private void showExpiredHelp() {
        logger.fine("Showing help information for 'expired' command");

        System.out.println("expired: Lists all items that have expired as of today.");
        if (verbose) {
            System.out.println("Usage: expired");
            System.out.println("  Shows all items with expiry dates before today's date");
            System.out.println("\nExample:");
            System.out.println("  expired");
            System.out.println("  This will show all items that have passed their expiry date");
        }
    }

    /**
     * Prints detailed information about the 'expiring' command.
     */
    private void showExpiringHelp() {
        logger.fine("Showing help information for 'expiring' command");

        System.out.println("expiring: Lists all items that will expire before a specified date.");
        if (verbose) {
            System.out.println("Usage: expiring <date>");
            System.out.println("  <date> - The cutoff date in yyyy-MM-dd format");
            System.out.println("  Shows all items with expiry dates before the specified date");
            System.out.println("\nExample:");
            System.out.println("  expiring 2024-12-31");
            System.out.println("  This will show all items expiring before December 31, 2024");
        }
        System.out.println("\nCorrect input format: expiring yyyy-MM-dd");
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
     * Prints detailed information about the 'order' command.
     */
    private void showOrderHelp() {
        logger.fine("Showing help information for 'order' command");

        System.out.println("order: Creates a new purchase or dispense order.");
        if (verbose) {
            System.out.println("Usage: order <type> <item1> <quantity1> [item2 quantity2 ...] [-n \"notes\"]");
            System.out.println("  <type>     - Type of order: 'purchase' or 'dispense'");
            System.out.println("  <itemN>    - Name of item to order");
            System.out.println("  <quantityN>- Quantity of the item");
            System.out.println("  -n         - Optional notes about the order");
            System.out.println("\nExamples:");
            System.out.println("  order purchase Aspirin 100 Bandages 50 -n \"Monthly stock replenishment\"");
            System.out.println("  order dispense Paracetamol 20 -n \"Emergency room request\"");
        }
    }

    /**
     * Prints detailed information about the 'fulfill-order' command.
     */
    private void showFulfillOrderHelp() {
        logger.fine("Showing help information for 'fulfill-order' command");

        System.out.println("fulfill-order: Processes and completes a pending order.");
        if (verbose) {
            System.out.println("Usage: fulfill-order <order-id>");
            System.out.println("  <order-id> - The unique identifier of the order to fulfill");
            System.out.println("\nExample:");
            System.out.println("  fulfill-order 123e4567-e89b-12d3-a456-556642440000");
            System.out.println("\nNote: This will create the necessary transactions and update inventory levels");
        }
    }

    /**
     * Prints detailed information about the 'cancel-order' command.
     */
    private void showCancelOrderHelp() {
        logger.fine("Showing help information for 'cancel-order' command");

        System.out.println("cancel-order: Cancels a pending order.");
        if (verbose) {
            System.out.println("Usage: cancel-order <order-id>");
            System.out.println("  <order-id> - The unique identifier of the order to cancel");
            System.out.println("\nExample:");
            System.out.println("  cancel-order 123e4567-e89b-12d3-a456-556642440000");
            System.out.println("\nNote: Only pending orders can be cancelled");
        }
    }

    /**
     * Prints detailed information about the 'list-orders' command.
     */
    private void showListOrdersHelp() {
        logger.fine("Showing help information for 'list-orders' command");

        System.out.println("list-orders: Lists all orders with optional filtering.");
        if (verbose) {
            System.out.println("Usage: list-orders [type] [status] [start-date] [end-date]");
            System.out.println("  [type]      - Optional: Filter by 'purchase' or 'dispense'");
            System.out.println("  [status]    - Optional: Filter by 'pending', 'fulfilled', or 'cancelled'");
            System.out.println("  [start-date]- Optional: Show orders from this date (yyyy-MM-dd)");
            System.out.println("  [end-date]  - Optional: Show orders until this date (yyyy-MM-dd)");
            System.out.println("\nExamples:");
            System.out.println("  list-orders");
            System.out.println("  list-orders purchase pending");
            System.out.println("  list-orders dispense fulfilled 2024-01-01 2024-12-31");
        }
    }

    /**
     * Prints detailed information about the 'transaction-history' command.
     */
    private void showTransactionHistoryHelp() {
        logger.fine("Showing help information for 'transaction-history' command");

        System.out.println("transaction-history: Views transaction history with optional filtering.");
        if (verbose) {
            System.out.println("Usage: transaction-history [item-name] [type] [start-date] [end-date]");
            System.out.println("  [item-name] - Optional: Filter by specific item");
            System.out.println("  [type]      - Optional: Filter by 'incoming' or 'outgoing'");
            System.out.println("  [start-date]- Optional: Show transactions from this date (yyyy-MM-dd)");
            System.out.println("  [end-date]  - Optional: Show transactions until this date (yyyy-MM-dd)");
            System.out.println("\nExamples:");
            System.out.println("  transaction-history");
            System.out.println("  transaction-history Aspirin");
            System.out.println("  transaction-history incoming 2024-01-01 2024-12-31");
            System.out.println("  transaction-history Bandages outgoing 2024-01-01 2024-12-31");
        }
    }

    /**
     * Prints detailed information about the 'stock-check' command.
     */
    private void showStockCheckHelp() {
        logger.fine("Showing help information for 'stock-check' command");

        System.out.println("stock-check: Displays all items in the inventory that need to be restocked.");
        if (verbose) {
            System.out.println("Usage: stock-check <threshold>");
            System.out.println("  <threshold> - Items with strictly less than this quantity will be printed.");
            System.out.println("\nExample:");
            System.out.println("  stock-check 100");
        }
        System.out.println("\nCorrect input format: stock-check <threshold>");
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
     * Determines whether this command will exit the application.
     *
     * @return - false as the help command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
