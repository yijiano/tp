package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.TransactionManager;
import seedu.pill.util.Order;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrderCommandTest {
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
    public void execute_purchaseOrder_createsOrder() throws PillException {
        ItemMap itemsToOrder = new ItemMap();
        itemsToOrder.addItemSilent(new Item("Paracetamol", 10));
        String notes = "test";

        OrderCommand command = new OrderCommand(itemsToOrder, transactionManager,
                Order.OrderType.PURCHASE, notes);
        command.execute(itemMap, storage);

        assertEquals(1, transactionManager.getOrders().size());
        Order createdOrder = transactionManager.getOrders().get(0);
        assertEquals(Order.OrderType.PURCHASE, createdOrder.getType());
    }

    @Test
    public void execute_dispenseOrder_createsOrder() throws PillException {
        ItemMap itemsToOrder = new ItemMap();
        itemsToOrder.addItemSilent(new Item("Paracetamol", 5));
        String notes = "test";

        OrderCommand command = new OrderCommand(itemsToOrder, transactionManager,
                Order.OrderType.DISPENSE, notes);
        command.execute(itemMap, storage);

        assertEquals(1, transactionManager.getOrders().size());
        Order createdOrder = transactionManager.getOrders().get(0);
        assertEquals(Order.OrderType.DISPENSE, createdOrder.getType());
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        OrderCommand command = new OrderCommand(new ItemMap(), transactionManager,
                Order.OrderType.PURCHASE, "");
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
