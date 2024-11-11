package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Order;
import seedu.pill.util.TransactionManager;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for ViewOrdersCommand
 */
public class ViewOrdersCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private TransactionManager transactionManager;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        transactionManager = new TransactionManager(itemMap, storage);
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @Test
    public void execute_withOrders_listsPendingOrders() throws PillException {
        // Create an order with some items
        ItemMap orderItems = new ItemMap();
        orderItems.addItemSilent(new Item("Paracetamol", 10));
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, orderItems, "Test order");

        ViewOrdersCommand command = new ViewOrdersCommand(transactionManager);
        command.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Paracetamol"));
        assertTrue(output.contains("10 in stock"));
        assertTrue(output.contains("Test order"));
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        ViewOrdersCommand command = new ViewOrdersCommand(transactionManager);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
