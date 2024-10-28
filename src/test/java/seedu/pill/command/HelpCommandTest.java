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
        assertTrue(output.contains("Type 'help <command>' for more information"));
    }

    @Test
    void execute_nullCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand(null, false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("Type 'help <command>' for more information"));
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
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_verboseHelpForDelete_printsDetailedDeleteHelp() throws PillException {
        HelpCommand command = new HelpCommand("delete", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: delete <name> <expiry>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForEdit_printsDetailedEditHelp() throws PillException {
        HelpCommand command = new HelpCommand("edit", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: edit <name> <quantity> <expiry>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForList_printsDetailedListHelp() throws PillException {
        HelpCommand command = new HelpCommand("list", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: list"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForStockCheck_printsDetailedStockCheckHelp() throws PillException {
        HelpCommand command = new HelpCommand("stock-check", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: stock-check <threshold>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForExit_printsDetailedExitHelp() throws PillException {
        HelpCommand command = new HelpCommand("exit", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: exit"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_invalidCommand_suggestsSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("hel", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: help?"));
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_veryInvalidCommand_showsNoSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("xyz", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
        assertTrue(output.contains("No similar command found"));
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
    void execute_helpWithMultipleWords_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("help more words", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_multiWordInvalidCommand_showsNoSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("invalid command string", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
        assertTrue(output.contains("No similar command found"));
    }

    @Test
    void execute_almostList_suggestsListCommand() throws PillException {
        HelpCommand command = new HelpCommand("lst", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: list?"));
    }

    @Test
    void execute_almostAdd_suggestsAddCommand() throws PillException {
        HelpCommand command = new HelpCommand("ad", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: add?"));
    }

    @Test
    void execute_almostDelete_suggestsDeleteCommand() throws PillException {
        HelpCommand command = new HelpCommand("delet", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: delete?"));
    }

    @Test
    void execute_almostEdit_suggestsEditCommand() throws PillException {
        HelpCommand command = new HelpCommand("edt", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: edit?"));
    }

    @Test
    void execute_almostStockCheck_suggestsStockCheckCommand() throws PillException {
        HelpCommand command = new HelpCommand("stock-chek", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: stock-check?"));
    }

    @Test
    void execute_almostExit_suggestsExitCommand() throws PillException {
        HelpCommand command = new HelpCommand("ext", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: exit?"));
    }

    @Test
    void execute_commandWithExtraSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help    -v", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
    }

    @Test
    void execute_mixedCaseCommand_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("HeLp", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_commandWithTrailingSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help   ", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_verboseWithExtraFlags_stillShowsVerbose() throws PillException {
        HelpCommand command = new HelpCommand("help -v -x", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
        assertTrue(output.contains("Examples:"));
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
    void execute_emptyStringCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("    ", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_helpWithNumbers_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help123", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
    }

    @Test
    void execute_caseInsensitiveVerboseFlag_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help -V", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
    }

    @Test
    void execute_generalHelp_includesNewCommands() throws PillException {
        HelpCommand command = new HelpCommand("", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("expired       - Lists all items that have expired"));
        assertTrue(output.contains("expiring      - Lists items expiring before a specified date"));
    }

    @Test
    void execute_expiredHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("expired", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("expired: Lists all items that have expired as of today"));
    }

    @Test
    void execute_verboseExpiredHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("expired", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: expired"));
        assertTrue(output.contains("Shows all items with expiry dates before today's date"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_expiringHelp_printsBasicHelp() throws PillException {
        HelpCommand command = new HelpCommand("expiring", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("expiring: Lists all items that will expire before a specified date"));
        assertTrue(output.contains("Correct input format: expiring yyyy-MM-dd"));
    }

    @Test
    void execute_verboseExpiringHelp_printsDetailedHelp() throws PillException {
        HelpCommand command = new HelpCommand("expiring", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: expiring <date>"));
        assertTrue(output.contains("<date> - The cutoff date in yyyy-MM-dd format"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("expiring 2024-12-31"));
    }

    @Test
    void execute_almostExpired_suggestsExpiredCommand() throws PillException {
        HelpCommand command = new HelpCommand("expire", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: expired?"));
    }

    @Test
    void execute_almostExpiring_suggestsExpiringCommand() throws PillException {
        HelpCommand command = new HelpCommand("expirin", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: expiring?"));
    }

    @Test
    void execute_mixedCaseExpired_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("ExPiReD", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("expired: Lists all items that have expired"));
    }

    @Test
    void execute_mixedCaseExpiring_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("ExPiRiNg", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("expiring: Lists all items that will expire"));
    }

    @Test
    void execute_expiringWithExtraSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("expiring    -v", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: expiring <date>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_expiredWithExtraSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("expired    -v", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: expired"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForAdd_printsDetailedAddHelp() throws PillException {
        HelpCommand command = new HelpCommand("add", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: add <name> <quantity> <expiry>"));
        assertTrue(output.contains("<name>     - Name of the item"));
        assertTrue(output.contains("<quantity> - Initial quantity of the item"));
        assertTrue(output.contains("<expiry>   - Expiry date of the item in yyyy-MM-dd format"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("add Aspirin 100 2024-05-24"));
        assertTrue(output.contains("\nCorrect input format: add <name> <quantity> <expiry>"));
    }

    @Test
    void execute_commandWithNoCloseMatch_printsNoSuggestion() throws PillException {
        // Using a completely different string that won't match any command
        HelpCommand command = new HelpCommand("xyzabc", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command: xyzabc"));
        assertTrue(output.contains("No similar command found"));
        assertTrue(output.contains("Available commands:"));
        assertFalse(output.contains("Did you mean:"));
    }

    @Test
    void execute_veryDifferentCommand_handleNullMatch() throws PillException {
        // Using a long string that's definitely more than 2 edits away from any command
        // This ensures StringMatcher.findClosestMatch returns null
        HelpCommand command = new HelpCommand("pneumonoultramicroscopicsilicovolcanoconiosis", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command: pneumonoultramicroscopicsilicovolcanoconiosis"));
        assertTrue(output.contains("No similar command found")); // This only appears in the else branch
        assertTrue(output.contains("Available commands:")); // Common output
        assertFalse(output.contains("Did you mean:")); // This shouldn't appear when there's no match
    }

    @Test
    void execute_similarCommand_handleNonNullMatch() throws PillException {
        HelpCommand command = new HelpCommand("ad", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        // Checking only what actually appears in the output
        assertTrue(output.contains("Did you mean: add?"));
        assertTrue(output.contains("add: Adds a new item to the inventory"));
    }

    @Test
    void execute_noSimilarCommand_handleNullMatch() throws PillException {
        HelpCommand command = new HelpCommand("xyzabc", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();

        // Checking only what actually appears in the output
        assertTrue(output.contains("Unknown command: xyzabc"));
        assertTrue(output.contains("No similar command found"));
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_orderHelp_printsBasicOrderHelp() throws PillException {
        HelpCommand command = new HelpCommand("order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("order: Creates a new purchase or dispense order"));
        assertFalse(output.contains("Example:")); // Basic help shouldn't show examples
    }

    @Test
    void execute_verboseOrderHelp_printsDetailedOrderHelp() throws PillException {
        HelpCommand command = new HelpCommand("order", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: order <type> <item1> <quantity1> [item2 quantity2 ...] [-n \"notes\"]"));
        assertTrue(output.contains("<type>     - Type of order: 'purchase' or 'dispense'"));
        assertTrue(output.contains("Examples:"));
        assertTrue(output.contains("order purchase Aspirin 100 Bandages 50"));
        assertTrue(output.contains("order dispense Paracetamol 20"));
    }

    @Test
    void execute_fulfillOrderHelp_printsBasicFulfillOrderHelp() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("fulfill-order: Processes and completes a pending order"));
        assertFalse(output.contains("Example:")); // Basic help shouldn't show examples
    }

    @Test
    void execute_verboseFulfillOrderHelp_printsDetailedFulfillOrderHelp() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-order", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: fulfill-order <order-id>"));
        assertTrue(output.contains("<order-id> - The unique identifier of the order to fulfill"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("This will create the necessary transactions"));
    }

    @Test
    void execute_transactionHistoryHelp_printsBasicTransactionHelp() throws PillException {
        HelpCommand command = new HelpCommand("transaction-history", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("transaction-history: Views transaction history with optional filtering"));
        assertFalse(output.contains("Example:")); // Basic help shouldn't show examples
    }

    @Test
    void execute_verboseTransactionHistoryHelp_printsDetailedTransactionHelp() throws PillException {
        HelpCommand command = new HelpCommand("transaction-history", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: transaction-history [item-name] [type] [start-date] [end-date]"));
        assertTrue(output.contains("[type]      - Optional: Filter by 'incoming' or 'outgoing'"));
        assertTrue(output.contains("Examples:"));
        assertTrue(output.contains("transaction-history Aspirin"));
        assertTrue(output.contains("transaction-history incoming 2024-01-01 2024-12-31"));
    }

    @Test
    void execute_almostOrder_suggestsOrderCommand() throws PillException {
        HelpCommand command = new HelpCommand("ordr", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: order?"));
    }

    @Test
    void execute_almostFulfillOrder_suggestsFulfillOrderCommand() throws PillException {
        HelpCommand command = new HelpCommand("fulfill-ordr", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: fulfill-order?"));
    }

    @Test
    void execute_almostTransactionHistory_suggestsTransactionHistoryCommand() throws PillException {
        HelpCommand command = new HelpCommand("transaction-histry", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: transaction-history?"));
    }

    @Test
    void execute_listOrdersHelp_printsBasicListOrdersHelp() throws PillException {
        HelpCommand command = new HelpCommand("list-orders", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("list-orders: Lists all orders with optional filtering"));
        assertFalse(output.contains("Example:")); // Basic help shouldn't show examples
    }

    @Test
    void execute_verboseListOrdersHelp_printsDetailedListOrdersHelp() throws PillException {
        HelpCommand command = new HelpCommand("list-orders", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: list-orders [type] [status] [start-date] [end-date]"));
        assertTrue(output.contains("[type]      - Optional: Filter by 'purchase' or 'dispense'"));
        assertTrue(output.contains("[status]    - Optional: Filter by 'pending', 'fulfilled', or 'cancelled'"));
        assertTrue(output.contains("Examples:"));
        assertTrue(output.contains("list-orders purchase pending"));
    }

    @Test
    void execute_cancelOrderHelp_printsBasicCancelOrderHelp() throws PillException {
        HelpCommand command = new HelpCommand("cancel-order", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("cancel-order: Cancels a pending order"));
        assertFalse(output.contains("Example:")); // Basic help shouldn't show examples
    }

    @Test
    void execute_verboseCancelOrderHelp_printsDetailedCancelOrderHelp() throws PillException {
        HelpCommand command = new HelpCommand("cancel-order", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: cancel-order <order-id>"));
        assertTrue(output.contains("<order-id> - The unique identifier of the order to cancel"));
        assertTrue(output.contains("Example:"));
        assertTrue(output.contains("Note: Only pending orders can be cancelled"));
    }

    @Test
    void execute_helpWithOrderRelatedTypos_suggestsCorrectCommands() throws PillException {
        // Test multiple similar typos for order-related commands
        String[] typos = {
                "order-list", "orderlist", "listorder", "list-ordr",
                "cancle-order", "cancel-ordr", "cancelorder",
                "fulfill", "fulfil-order", "fullfill-order"
        };

        // StringBuilder to collect all outputs
        StringBuilder allOutput = new StringBuilder();

        for (String typo : typos) {
            HelpCommand command = new HelpCommand(typo, false);
            command.execute(itemMap, storage);
            allOutput.append(outContent.toString());
            outContent.reset(); // Clear for next iteration
        }

        // Check for presence of suggestions in combined output
        String output = allOutput.toString();
        assertTrue(output.contains("Did you mean: list-orders?"),
                "Should suggest 'list-orders' for list-related typos");
        assertTrue(output.contains("Did you mean: cancel-order?"),
                "Should suggest 'cancel-order' for cancel-related typos");
        assertTrue(output.contains("Did you mean: fulfill-order?"),
                "Should suggest 'fulfill-order' for fulfill-related typos");
    }

    @AfterEach
    void restoreSystemStreams() {
        System.setOut(originalOut);
    }
}
