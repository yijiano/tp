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
        assertFalse(output.contains("Usage:")); // Basic help shouldn't show verbose info
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
        assertFalse(output.contains("Usage:")); // Basic help shouldn't show verbose information
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

    @AfterEach
    void restoreSystemStreams() {
        System.setOut(originalOut);
    }
}