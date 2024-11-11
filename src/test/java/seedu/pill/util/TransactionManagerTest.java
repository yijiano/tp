package seedu.pill.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

class TransactionManagerTest {
    private static final String VALID_ITEM_NAME = "Aspirin";
    private static final int VALID_QUANTITY = 100;
    private static final String VALID_NOTES = "Test notes";
    private TransactionManager transactionManager;
    private ItemMap itemMap;

    @BeforeEach
    void setUp() {
        itemMap = new ItemMap();
        transactionManager = new TransactionManager(itemMap);
    }

    @Test
    void createTransaction_zeroQuantity_throwsException() {
        assertThrows(AssertionError.class, () ->
                transactionManager.createTransaction(
                        VALID_ITEM_NAME,
                        0,
                        Transaction.TransactionType.INCOMING,
                        VALID_NOTES,
                        null
                )
        );
    }

    @Test
    void createTransaction_negativeQuantity_throwsException() {
        assertThrows(AssertionError.class, () ->
                transactionManager.createTransaction(
                        VALID_ITEM_NAME,
                        -1,
                        Transaction.TransactionType.INCOMING,
                        VALID_NOTES,
                        null
                )
        );
    }

    @Test
    void createTransaction_outgoingWithInsufficientStock_throwsException() {
        assertThrows(PillException.class, () ->
                transactionManager.createTransaction(
                        VALID_ITEM_NAME,
                        VALID_QUANTITY,
                        Transaction.TransactionType.OUTGOING,
                        VALID_NOTES,
                        null
                )
        );
    }

    // Tests for Order-related functionality
    @Test
    void createOrder_validPurchaseOrder_createsSuccessfully() {
        Order order = transactionManager.createOrder(
                Order.OrderType.PURCHASE,
                new ItemMap(),
                VALID_NOTES
        );

        assertNotNull(order);
        assertEquals(Order.OrderType.PURCHASE, order.getType());
        assertEquals(VALID_NOTES, order.getNotes());
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void fulfillOrder_purchaseOrder_updatesInventoryAndStatus() throws PillException {
        // Arrange
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), "Test purchase");
        String itemName = "Aspirin";
        int quantity = 100;
        order.addItem(new Item(itemName, quantity));

        // Act
        transactionManager.fulfillOrder(order);

        // Assert
        assertEquals(Order.OrderStatus.FULFILLED, order.getStatus());
        assertNotNull(order.getFulfillmentTime());

        // Verify inventory was updated
        TreeSet<Item> items = itemMap.get(itemName);
        assertEquals(1, items.size());
        assertEquals(quantity, items.first().getQuantity());
    }

    @Test
    void fulfillOrder_dispenseOrder_updatesInventoryAndStatus() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        int initialQuantity = 100;
        int dispenseQuantity = 30;

        // Add initial stock
        transactionManager.createTransaction(
                itemName,
                initialQuantity,
                Transaction.TransactionType.INCOMING,
                "Initial stock",
                null
        );

        // Create and fulfill dispense order
        Order order = transactionManager.createOrder(Order.OrderType.DISPENSE, new ItemMap(), "Test dispense");
        order.addItem(new Item(itemName, dispenseQuantity));

        // Act
        transactionManager.fulfillOrder(order);

        // Assert
        assertEquals(Order.OrderStatus.FULFILLED, order.getStatus());
        assertNotNull(order.getFulfillmentTime());

        // Verify inventory was updated
        TreeSet<Item> items = itemMap.get(itemName);
        assertEquals(1, items.size());
        assertEquals(initialQuantity - dispenseQuantity, items.first().getQuantity());
    }

    @Test
    void fulfillOrder_nonPendingOrder_throwsException() throws PillException {
        // Arrange
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), "Test order");
        order.fulfill(); // Change status to FULFILLED

        // Act & Assert
        assertThrows(PillException.class, () -> transactionManager.fulfillOrder(order));
    }

    // Tests for Transaction History
    @Test
    void getTransactions_returnsCorrectTransactions() throws PillException {
        // Arrange
        transactionManager.createTransaction("Aspirin", 100,
                Transaction.TransactionType.INCOMING, "First", null);
        transactionManager.createTransaction("Bandage", 50,
                Transaction.TransactionType.INCOMING, "Second", null);

        // Act
        List<Transaction> transactions = transactionManager.getTransactions();

        // Assert
        assertEquals(2, transactions.size());
        assertEquals("Aspirin", transactions.get(0).getItemName());
        assertEquals("Bandage", transactions.get(1).getItemName());
    }

    @Test
    void getOrders_returnsCorrectOrders() {
        // Arrange
        transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), "First order");
        transactionManager.createOrder(Order.OrderType.DISPENSE, new ItemMap(), "Second order");

        // Act
        List<Order> orders = transactionManager.getOrders();

        // Assert
        assertEquals(2, orders.size());
        assertEquals(Order.OrderType.PURCHASE, orders.get(0).getType());
        assertEquals(Order.OrderType.DISPENSE, orders.get(1).getType());
    }

    @Test
    void getItemTransactions_returnsCorrectTransactions() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        transactionManager.createTransaction(itemName, 100,
                Transaction.TransactionType.INCOMING, "First", null);
        transactionManager.createTransaction("Bandage", 50,
                Transaction.TransactionType.INCOMING, "Second", null);
        transactionManager.createTransaction(itemName, 30,
                Transaction.TransactionType.OUTGOING, "Third", null);

        // Act
        List<Transaction> transactions = transactionManager.getItemTransactions(itemName);

        // Assert
        assertEquals(2, transactions.size());
        assertTrue(transactions.stream().allMatch(t -> t.getItemName().equals(itemName)));
    }

    @Test
    void getTransactionHistory_returnsTransactionsInTimeRange() throws PillException {
        // Arrange
        LocalDate startDate = LocalDate.now();

        transactionManager.createTransaction("Aspirin", 100,
                Transaction.TransactionType.INCOMING, "First", null);
        transactionManager.createTransaction("Bandage", 50,
                Transaction.TransactionType.INCOMING, "Second", null);

        LocalDate endDate = LocalDate.now();

        // Act
        List<Transaction> transactions = transactionManager.getTransactionHistory(startDate, endDate);

        // Assert
        assertEquals(2, transactions.size());
        assertTrue(transactions.stream()
                .allMatch(t -> !t.getTimestamp().toLocalDate().isBefore(startDate) && !t.getTimestamp().toLocalDate()
                        .isAfter(endDate)));
    }

    @Test
    void fulfillOrder_multipleItems_updatesInventoryCorrectly() throws PillException {
        // Arrange
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), "Multi-item order");
        order.addItem(new Item("Aspirin", 100));
        order.addItem(new Item("Bandage", 50));
        order.addItem(new Item("Syringe", 25));

        // Act
        transactionManager.fulfillOrder(order);

        // Assert
        assertEquals(Order.OrderStatus.FULFILLED, order.getStatus());

        // Verify inventory for all items
        assertEquals(100, itemMap.get("Aspirin").first().getQuantity());
        assertEquals(50, itemMap.get("Bandage").first().getQuantity());
        assertEquals(25, itemMap.get("Syringe").first().getQuantity());
    }

    @Test
    void createTransaction_withExpiryDate_handlesCorrectly() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        int quantity = 100;
        LocalDate expiryDate = LocalDate.now().plusYears(1);

        // Create item with expiry date
        Item item = new Item(itemName, quantity, expiryDate);
        itemMap.addItem(item);

        // Act
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                50,
                Transaction.TransactionType.OUTGOING,
                "Test with expiry",
                null
        );

        // Assert
        assertNotNull(transaction);
        assertEquals(50, itemMap.get(itemName).first().getQuantity());
    }

    @Test
    void createTransaction_outgoingPartialQuantity_updatesInventoryCorrectly() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        int initialQuantity = 100;
        int decreaseQuantity = 30;  // Less than initial quantity

        // Add initial stock
        transactionManager.createTransaction(
                itemName,
                initialQuantity,
                Transaction.TransactionType.INCOMING,
                "Initial stock",
                null
        );

        // Act
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                decreaseQuantity,
                Transaction.TransactionType.OUTGOING,
                "Partial withdrawal",
                null
        );

        // Assert
        assertNotNull(transaction);
        assertEquals(decreaseQuantity, transaction.getQuantity());

        // Verify inventory was partially reduced
        TreeSet<Item> items = itemMap.get(itemName);
        assertEquals(1, items.size());
        assertEquals(initialQuantity - decreaseQuantity, items.first().getQuantity());
    }

    @Test
    void createTransaction_outgoingExactQuantity_removesItem() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        int initialQuantity = 100;

        // Add initial stock
        transactionManager.createTransaction(
                itemName,
                initialQuantity,
                Transaction.TransactionType.INCOMING,
                "Initial stock",
                null
        );

        // Act
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                initialQuantity,  // Withdraw exact quantity
                Transaction.TransactionType.OUTGOING,
                "Complete withdrawal",
                null
        );

        // Assert
        assertNotNull(transaction);
        assertEquals(initialQuantity, transaction.getQuantity());

        // Verify item was completely removed
        TreeSet<Item> items = itemMap.get(itemName);
        assertTrue(items.isEmpty());
    }

    @Test
    void createTransaction_outgoingMultipleItems_handlesCorrectly() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        LocalDate expiry1 = LocalDate.now().plusMonths(1);
        LocalDate expiry2 = LocalDate.now().plusMonths(2);

        // Add two batches with different expiry dates
        itemMap.addItem(new Item(itemName, 50, expiry1));
        itemMap.addItem(new Item(itemName, 100, expiry2));

        // Act - withdraw amount that spans both items
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                70,  // This will use up the first batch (50) and part of the second (20)
                Transaction.TransactionType.OUTGOING,
                "Multi-batch withdrawal",
                null
        );

        // Assert
        assertNotNull(transaction);
        assertEquals(70, transaction.getQuantity());

        // Verify inventory state
        TreeSet<Item> items = itemMap.get(itemName);
        assertEquals(1, items.size());  // Only second batch should remain
        Item remainingItem = items.first();
        assertEquals(expiry2, remainingItem.getExpiryDate().get());
        assertEquals(80, remainingItem.getQuantity());  // 100 - 20 = 80
    }

    @Test
    void getTransactionHistory_exactTimeRange_returnsMatchingTransactions() throws PillException {
        // Arrange
        LocalDate start = LocalDate.now();
        Transaction first = transactionManager.createTransaction(
                "Aspirin", 100, Transaction.TransactionType.INCOMING, "First", null);
        Transaction second = transactionManager.createTransaction(
                "Bandage", 50, Transaction.TransactionType.INCOMING, "Second", null);
        LocalDate end = LocalDate.now();

        // Act
        List<Transaction> transactions = transactionManager.getTransactionHistory(
                first.getTimestamp().toLocalDate(), second.getTimestamp().toLocalDate());

        assertEquals(2, history.size());
        assertTrue(history.contains(transaction1));
        assertTrue(history.contains(transaction2));
    }

    @Test
    void getTransactionHistory_exactBoundaryTimes_includesTransactions() throws PillException {
        // Arrange
        Transaction first = transactionManager.createTransaction(
                "First", 100, Transaction.TransactionType.INCOMING, "Boundary start", null);
        LocalDate startAndEnd = first.getTimestamp().toLocalDate(); // Use exact timestamp

        // Act
        List<Transaction> transactions = transactionManager.getTransactionHistory(startAndEnd, startAndEnd);

        // Assert
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(first));
    }

    @Test
    void getTransactionHistory_endBeforeStart_returnsEmptyList() throws PillException {
        // Arrange
        Transaction transaction = transactionManager.createTransaction(
                "Test", 100, Transaction.TransactionType.INCOMING, "Test", null);
        LocalDate later = LocalDate.now();
        LocalDate earlier = later.minusDays(1);

        // Act
        List<Transaction> transactions = transactionManager.getTransactionHistory(later, earlier);

        // Assert
        assertTrue(transactions.isEmpty());
    }
}
