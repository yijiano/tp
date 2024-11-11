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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for FulfillCommand
 */
public class FulfillCommandTest {
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
    public void execute_insufficientStock_throwsException() {
        // Create dispense order without sufficient stock
        ItemMap orderItems = new ItemMap();
        orderItems.addItemSilent(new Item("Paracetamol", 10));
        Order order = transactionManager.createOrder(Order.OrderType.DISPENSE, orderItems, "Test failure");

        FulfillCommand command = new FulfillCommand(order, transactionManager);

        assertThrows(PillException.class, () -> command.execute(itemMap, storage));
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        ItemMap orderItems = new ItemMap();
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, orderItems, "Test");
        FulfillCommand command = new FulfillCommand(order, transactionManager);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
