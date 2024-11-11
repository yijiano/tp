package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelpCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void execute_emptyCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("\nItem Management:"));
        assertTrue(output.contains("\nOther Commands:"));
    }

    @Test
    void execute_generalHelpCategories_showsAllCategories() throws PillException {
        HelpCommand command = new HelpCommand("", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("\nItem Management:"));
        assertTrue(output.contains("\nPrice and Cost Management:"));
        assertTrue(output.contains("\nOrder Management:"));
        assertTrue(output.contains("\nTransaction Management:"));
        assertTrue(output.contains("\nOther Commands:"));
    }

    @Test
    void execute_nullCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand(null, false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("\nItem Management:"));
    }

    @Test
    void execute_helpForHelp_printsHelpHelp() throws PillException {
        HelpCommand command = new HelpCommand("help", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_verboseHelpForHelp_printsDetailedHelpHelp() throws PillException {
        HelpCommand command = new HelpCommand("help", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
        assertTrue(output.contains("[command] - Optional. Specify a command to get detailed help."));
        assertTrue(output.contains("[-v]      - Optional. Show verbose output with examples."));
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_addCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("add", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("add: Adds a new item to the inventory"));
        assertFalse(output.contains("Usage:"));
    }

    @Test
    void execute_verboseAddHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("add", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: add <name> <quantity> <expiry>"));
        assertTrue(output.contains("<name>     - Name of the item"));
        assertTrue(output.contains("[quantity] - Optional: Initial quantity of the item"));
        assertTrue(output.contains("[expiry]   - Optional: Expiry date of the item"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_deleteCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("delete", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("delete: Removes an item from the inventory"));
    }

    @Test
    void execute_editCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("edit", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("edit: Edits the item in the inventory"));
    }

    @Test
    void execute_findCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("find", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("find: Finds all items with the same keyword"));
    }

    @Test
    void execute_useCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("use", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("use: Priority removal of items from the list"));
    }

    @Test
    void execute_restockCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("restock", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("restock: Restocks a specified item"));
    }

    @Test
    void execute_restockAllCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("restock-all", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("restock-all: Restocks all items below a specified threshold."));
        assertFalse(output.contains("Usage:"));
    }

    @Test
    void execute_costCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("cost", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("cost: Sets the cost for a specified item"));
    }

    @Test
    void execute_priceCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("price", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("price: Sets the selling price for a specified item"));
    }

    @Test
    void execute_orderCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("order: Creates a new purchase or dispense order"));
    }

    @Test
    void execute_fulfillOrderCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("fulfill-order: Processes and completes a pending order"));
    }

    @Test
    void execute_viewOrdersCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("view-orders", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("view-orders: Lists all orders"));
    }

    @Test
    void execute_transactionsCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("transactions", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("transactions: Views all transactions"));
    }

    @Test
    void execute_transactionHistoryCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("transaction-history", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("transaction-history: Views transaction history"));
    }

    @Test
    void execute_verboseViewOrdersHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("view-orders", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: view-orders"));
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_verboseTransactionsHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("transactions", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: transactions"));
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_verboseTransactionHistoryHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("transaction-history", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: transaction-history <start-date> <end-date>"));
        assertTrue(output.contains("<start-date>  - Show transactions from this date"));
        assertTrue(output.contains("<end-date>    - Show transactions until this date"));
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_invalidCommand_suggestsSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("hel", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: help?"));
    }

    @Test
    void execute_noSimilarCommand_handleNullMatch() throws PillException {
        HelpCommand command = new HelpCommand("xyzabc", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command: xyzabc"));
        assertTrue(output.contains("No similar command found"));
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_nullItemMap_throwsAssertionError() {
        HelpCommand command = new HelpCommand("help", false);
        assertThrows(AssertionError.class, () -> command.execute(null, storage));
    }

    @Test
    void execute_nullStorage_throwsAssertionError() {
        HelpCommand command = new HelpCommand("help", false);
        assertThrows(AssertionError.class, () -> command.execute(itemMap, null));
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        HelpCommand command = new HelpCommand("help", false);
        assertFalse(command.isExit());
    }

    @Test
    void execute_mixedCaseCommand_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("HeLp", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_commandWithExtraSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help    -v", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
    }

    @Test
    void execute_helpWithSpecialCharacters_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help#$%", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_visualizePriceCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-price", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-price: Displays a bar chart of item prices"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_verboseVisualizePriceCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-price", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: visualize-price"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("This will display a chart of item prices"));
    }

    @Test
    void execute_visualizeCostCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-cost", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-cost: Displays a bar chart of item costs"));
    }

    @Test
    void execute_visualizeStockCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-stock", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-stock: Displays a bar chart of item stocks"));
    }

    @Test
    void execute_visualizeCostPriceCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-cost-price", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-cost-price: Displays a bar chart comparing item costs and prices"));
    }

    @Test
    void execute_verboseStockCheckCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("stock-check", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: stock-check <threshold>"));
        assertTrue(output.contains("<threshold> - Items with strictly less than this quantity will be printed"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseExpiredCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("expired", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: expired"));
        assertTrue(output.contains("Shows all items with expiry dates before today's date"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_commandWithMultipleWords_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("visualize-cost-price verbose", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-cost-price: Displays a bar chart"));
    }

    @Test
    void execute_similarCommandSuggestion_handlesCloseMatch() throws PillException {
        HelpCommand command = new HelpCommand("vsualize-price", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: visualize-price?"));
    }

    @Test
    void execute_whitespaceOnlyCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("   ", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_generalHelp_showsAllCommandCategories() throws PillException {
        HelpCommand command = new HelpCommand(null, false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("\nItem Management:"));
        assertTrue(output.contains("\nVisualization:"));
        assertTrue(output.contains("\nPrice and Cost Management:"));
        assertTrue(output.contains("\nOrder Management:"));
        assertTrue(output.contains("\nTransaction Management:"));
        assertTrue(output.contains("\nOther Commands:"));
    }

    @Test
    void execute_verboseFlagWithInvalidCommand_showsSuggestions() throws PillException {
        HelpCommand command = new HelpCommand("hlp", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: help?"));
    }

    @Test
    void execute_verboseFlagWithoutCommand_showsDetailedGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand(null, true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("Type 'help <command>' for more information"));
        assertTrue(output.contains("Type 'help <command> -v' for verbose output"));
    }

    @Test
    void execute_expiringCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("expiring", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("expiring: Lists all items that will expire before a specified date"));
        assertFalse(output.contains("Usage:"));
    }

    @Test
    void execute_verboseExpiringCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("expiring", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: expiring <date>"));
        assertTrue(output.contains("<date> - The cutoff date in yyyy-MM-dd format"));
        assertTrue(output.contains("Shows all items with expiry dates before the specified date"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("expiring 2024-12-31"));
        assertTrue(output.contains("This will show all items expiring before December 31, 2024"));
    }

    @Test
    void execute_listCommand_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("list", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("list: Displays all items in the inventory"));
        assertFalse(output.contains("Usage:"));
    }

    @Test
    void execute_verboseListCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("list", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: list"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("  list"));
    }

    @Test
    void execute_verboseVisualizeCostCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-cost", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-cost: Displays a bar chart of item costs"));
        assertTrue(output.contains("Usage: visualize-cost"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("  visualize-cost"));
        assertTrue(output.contains("  This will display a chart of item costs"));
    }

    @Test
    void execute_verboseVisualizeStockCommand_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("visualize-stock", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("visualize-stock: Displays a bar chart of item stocks"));
        assertTrue(output.contains("Usage: visualize-stock"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("  visualize-stock"));
        assertTrue(output.contains("  This will display a chart of item stocks"));
    }

    @Test
    void execute_verboseFindHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("find", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("find: Finds all items with the same keyword"));
        assertTrue(output.contains("Usage: find <keyword>"));
        assertTrue(output.contains("<keyword>     - Keyword to search for in item names"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("find Aspirin"));
        assertTrue(output.contains("Correct input format: find <name>"));
    }

    @Test
    void execute_verboseRestockHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("restock", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("restock: Restocks a specified item with an optional expiry date and quantity"));
        assertTrue(output.contains("Usage: restock <item-name> [expiry-date] <quantity>"));
        assertTrue(output.contains("<item-name>    - The name of the item to restock"));
        assertTrue(output.contains("[expiry-date]  - Optional. The expiry date of the item in yyyy-MM-dd format"));
        assertTrue(output.contains("[quantity]     - Optional. The quantity to restock up to. Defaults to 50"));
        assertTrue(output.contains("Examples:"));
        assertTrue(output.contains("restock apple 100"));
        assertTrue(output.contains("restock orange 2025-12-12 50"));
    }

    @Test
    void execute_verboseRestockAllHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("restock-all", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("restock-all: Restocks all items below a specified threshold"));
        assertTrue(output.contains("Usage: restockall [threshold]"));
        assertTrue(output.contains("[threshold] - Optional. The minimum quantity for restocking. Defaults to 50"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("restockall 100"));
    }

    @Test
    void execute_verboseCostHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("cost", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("cost: Sets the cost for a specified item"));
        assertTrue(output.contains("Usage: cost <item-name> <cost>"));
        assertTrue(output.contains("<item-name> - The name of the item"));
        assertTrue(output.contains("<cost>      - The cost value to set for the item"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("cost apple 20.0"));
    }

    @Test
    void execute_verbosePriceHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("price", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("price: Sets the selling price for a specified item"));
        assertTrue(output.contains("Usage: price <item-name> <price>"));
        assertTrue(output.contains("<item-name> - The name of the item"));
        assertTrue(output.contains("<price>     - The price value to set for the item"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("price apple 30.0"));
    }

    @Test
    void execute_basicUseHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("use", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("use: Priority removal of items from the list"));
        assertTrue(output.contains("Correct input format: use <name>"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicFindHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("find", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("find: Finds all items with the same keyword"));
        assertTrue(output.contains("Correct input format: find <name>"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicRestockHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("restock", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("restock: Restocks a specified item with an optional expiry date and quantity"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicRestockAllHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("restock-all", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("restock-all: Restocks all items below a specified threshold"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicCostHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("cost", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("cost: Sets the cost for a specified item"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicPriceHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("price", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("price: Sets the selling price for a specified item"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_verboseDeleteHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("delete", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("delete: Removes an item from the inventory"));
        assertTrue(output.contains("Usage: delete <name> <expiry>"));
        assertTrue(output.contains("<name> - Name of the item to delete (as shown in the list)"));
        assertTrue(output.contains("<expiry>   - Expiry date of the item in yyyy/MM/dd format"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("delete Aspirin 2024-05-24"));
        assertTrue(output.contains("Correct input format: delete <name> <expiry>"));
    }

    @Test
    void execute_deleteHelpCaseInsensitive_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("DeLeTe", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("delete: Removes an item from the inventory"));
        assertTrue(output.contains("Usage: delete <name> <expiry>"));
    }

    @Test
    void execute_deleteHelpWithExtraSpaces_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("delete   ", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("delete: Removes an item from the inventory"));
    }

    @Test
    void execute_verboseEditHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("edit", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("edit: Edits the item in the inventory"));
        assertTrue(output.contains("Usage: edit <name> <quantity> <expiry>"));
        assertTrue(output.contains("<name> - Name of the item to edit (as shown in the list)"));
        assertTrue(output.contains("<quantity> - New quantity of the item"));
        assertTrue(output.contains("<expiry>   - Expiry date of the item in yyyy-MM-dd format"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("edit Aspirin 100 2024-05-24"));
        assertTrue(output.contains("Correct input format: edit <name> <quantity> <expiry>"));
    }

    @Test
    void execute_editHelpCaseInsensitive_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("EdIt", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("edit: Edits the item in the inventory"));
        assertTrue(output.contains("Usage: edit <name> <quantity> <expiry>"));
    }

    @Test
    void execute_editHelpWithExtraSpaces_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("edit   ", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("edit: Edits the item in the inventory"));
    }

    @Test
    void execute_deleteHelpWithInvalidFlag_showsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("delete -x", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("delete: Removes an item from the inventory"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_editHelpWithInvalidFlag_showsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("edit -x", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("edit: Edits the item in the inventory"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicOrderHelp_showsBasicInformation() throws PillException {
        HelpCommand command = new HelpCommand("order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("order: Creates a new purchase or dispense order."));

        assertFalse(output.contains("Usage:"));
        assertFalse(output.contains("<type>     - Type of order: 'purchase' or 'dispense'"));
        assertFalse(output.contains("Examples:"));
    }

    @Test
    void execute_verboseOrderHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("order", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        assertTrue(output.contains("order: Creates a new purchase or dispense order."));

        assertTrue(output.contains("Usage:"));
        assertTrue(output.contains("order <type> <item-count>"));
        assertTrue(output.contains("<item1> <quantity1>"));
        assertTrue(output.contains("[item2 quantity2]"));
        assertTrue(output.contains("[-n \"notes\"]"));

        assertTrue(output.contains("<type>     - Type of order: 'purchase' or 'dispense'"));
        assertTrue(output.contains("<itemN>    - Name of item to order"));
        assertTrue(output.contains("<quantityN>- Quantity of the item"));
        assertTrue(output.contains("-n         - Optional notes about the order"));

        assertTrue(output.contains("Examples:"));
        assertTrue(output.contains("order purchase 2"));
        assertTrue(output.contains("Aspirin 100"));
        assertTrue(output.contains("Bandages 50"));
        assertTrue(output.contains("-n \"Monthly stock replenishment\""));

        assertTrue(output.contains("order dispense 1"));
        assertTrue(output.contains("Paracetamol 20"));
        assertTrue(output.contains("-n \"Emergency room request\""));
    }

    @Test
    void execute_orderHelpCaseInsensitive_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("OrDeR", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("order: Creates a new purchase or dispense order."));
        assertTrue(output.contains("Usage:"));
        assertTrue(output.contains("<type>     - Type of order: 'purchase' or 'dispense'"));
    }

    @Test
    void execute_orderHelpWithExtraSpaces_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("order    ", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("order: Creates a new purchase or dispense order."));
    }

    @Test
    void execute_basicFulfillOrderHelp_showsBasicInformation() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("fulfill-order: Processes and completes a pending order."));

        assertFalse(output.contains("Usage: fulfill-order <order-uuid>"));
        assertFalse(output.contains("Example:"));
        assertFalse(output.contains("Note: This will create the necessary transactions"));
    }

    @Test
    void execute_verboseFulfillOrderHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        assertTrue(output.contains("fulfill-order: Processes and completes a pending order."));

        assertTrue(output.contains("Usage: fulfill-order <order-uuid>"));
        assertTrue(output.contains("<order-uuid> - The unique identifier of the order to fulfill"));

        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("fulfill-order 123e4567-e89b-12d3-a456-556642440000"));

        assertTrue(output.contains("Note: This will create the necessary transactions and update inventory levels"));
    }

    @Test
    void execute_fulfillOrderHelpCaseInsensitive_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("FuLfIlL-OrDeR", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("fulfill-order: Processes and completes a pending order."));
        assertTrue(output.contains("Usage: fulfill-order <order-uuid>"));
    }

    @Test
    void execute_fulfillOrderHelpWithExtraSpaces_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order   ", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("fulfill-order: Processes and completes a pending order."));
    }

    @Test
    void execute_orderHelpWithInvalidFlag_showsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("order -x", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("order: Creates a new purchase or dispense order."));
        assertFalse(output.contains("Examples:"));
    }

    @Test
    void execute_fulfillOrderHelpWithInvalidFlag_showsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order -x", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("fulfill-order: Processes and completes a pending order."));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicExitHelp_showsBasicInformation() throws PillException {
        HelpCommand command = new HelpCommand("exit", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("exit: Exits the program."));

        assertFalse(output.contains("Usage: exit"));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_verboseExitHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("exit", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("exit: Exits the program."));
        assertTrue(output.contains("Usage: exit"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("  exit"));
    }

    @Test
    void execute_exitHelpCaseInsensitive_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("ExIt", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("exit: Exits the program."));
        assertTrue(output.contains("Usage: exit"));
    }

    @Test
    void execute_exitHelpWithExtraSpaces_showsHelp() throws PillException {
        HelpCommand command = new HelpCommand("exit   ", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("exit: Exits the program."));
    }

    @Test
    void execute_exitHelpWithInvalidFlag_showsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("exit -x", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("exit: Exits the program."));
        assertFalse(output.contains("Example:"));
    }

    @Test
    void execute_basicStockCheckHelp_showsBasicInformation() throws PillException {
        HelpCommand command = new HelpCommand("stock-check", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        assertTrue(output.contains("stock-check: Displays all items in the inventory that need to be restocked."));
        assertTrue(output.contains("Correct input format: stock-check <threshold>"));

        assertFalse(output.contains("Usage: stock-check <threshold>"));
        assertFalse(output.contains("<threshold> - Items with strictly less than this quantity will be printed."));
        assertFalse(output.contains("Example:"));
        assertFalse(output.contains("stock-check 100"));
    }

    @Test
    void execute_verboseStockCheckHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("stock-check", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        assertTrue(output.contains("stock-check: Displays all items in the inventory that need to be restocked."));
        assertTrue(output.contains("Correct input format: stock-check <threshold>"));

        assertTrue(output.contains("Usage: stock-check <threshold>"));
        assertTrue(output.contains("<threshold> - Items with strictly less than this quantity will be printed."));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("stock-check 100"));
    }

    @Test
    void execute_basicExpiredHelp_showsBasicInformation() throws PillException {
        HelpCommand command = new HelpCommand("expired", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        assertTrue(output.contains("expired: Lists all items that have expired as of today."));

        assertFalse(output.contains("Usage: expired"));
        assertFalse(output.contains("Shows all items with expiry dates before today's date"));
        assertFalse(output.contains("Example:"));
        assertFalse(output.contains("This will show all items that have passed their expiry date"));
    }

    @Test
    void execute_verboseExpiredHelp_showsDetailedInformation() throws PillException {
        HelpCommand command = new HelpCommand("expired", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        assertTrue(output.contains("expired: Lists all items that have expired as of today."));

        assertTrue(output.contains("Usage: expired"));
        assertTrue(output.contains("Shows all items with expiry dates before today's date"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("expired"));
        assertTrue(output.contains("This will show all items that have passed their expiry date"));
    }

    @AfterEach
    void restoreSystemStreams() {
        System.setOut(originalOut);
    }
}
