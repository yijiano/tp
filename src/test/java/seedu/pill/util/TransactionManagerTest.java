package seedu.pill.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

//@@author philip1304

class TransactionManagerTest {
    private TransactionManager transactionManager;
    private ItemMap itemMap;
    private ByteArrayOutputStream outContent;  // Changed variable name to match usage
    private final PrintStream originalOut = System.out;
    private Storage storage;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();  // Initialize here
        System.setOut(new PrintStream(outContent));
        itemMap = new ItemMap();
        storage = new Storage();
        transactionManager = new TransactionManager(itemMap, storage);
    }

    @Test
    void createTransaction_insufficientStock_throwsException() {
        // Arrange
        String itemName = "Aspirin";
        int initialQuantity = 50;
        int decreaseQuantity = 100;
        LocalDate expiryDate = null;

        assertThrows(PillException.class, () -> {
            transactionManager.createTransaction(
                    itemName,
                    decreaseQuantity,
                    expiryDate,
                    Transaction.TransactionType.OUTGOING,
                    "Test insufficient",
                    null
            );
        });
    }

    @Test
    void createTransaction_withOrder_associatesCorrectly() throws PillException {
        // Arrange
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), "Test order");
        String itemName = "Aspirin";
        int quantity = 100;
        LocalDate expiryDate = null;

        // Act
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                quantity,
                expiryDate,
                Transaction.TransactionType.INCOMING,
                "Test with order",
                order
        );

        // Assert
        assertNotNull(transaction);
        assertEquals(order, transaction.getAssociatedOrder());
    }

    @Test
    void createOrder_purchaseOrder_createsSuccessfully() {
        // Arrange
        String notes = "Test purchase order";

        // Act
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), notes);

        // Assert
        assertNotNull(order);
        assertEquals(Order.OrderType.PURCHASE, order.getType());
        assertEquals(notes, order.getNotes());
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void createOrder_dispenseOrder_createsSuccessfully() {
        // Arrange
        String notes = "Test dispense order";

        // Act
        Order order = transactionManager.createOrder(Order.OrderType.DISPENSE, new ItemMap(), notes);

        // Assert
        assertNotNull(order);
        assertEquals(Order.OrderType.DISPENSE, order.getType());
        assertEquals(notes, order.getNotes());
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void fulfillOrder_nonPendingOrder_throwsException() throws PillException {
        // Arrange
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), "Test order");
        order.fulfill(); // Change status to FULFILLED

        // Act & Assert
        assertThrows(PillException.class, () -> transactionManager.fulfillOrder(order));
    }

    @Test
    void getTransactions_returnsCorrectTransactions() throws PillException {
        // Arrange
        transactionManager.createTransaction("Aspirin", 100, null,
                Transaction.TransactionType.INCOMING, "First", null);
        transactionManager.createTransaction("Bandage", 50, null,
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
    void getTransactionHistory_returnsTransactionsInTimeRange() throws PillException {
        // Arrange
        LocalDate startDate = LocalDate.now();

        transactionManager.createTransaction("Aspirin", 100, null,
                Transaction.TransactionType.INCOMING, "First", null);
        transactionManager.createTransaction("Bandage", 50, null,
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
    void getTransactionHistory_exactTimeRange_returnsMatchingTransactions() throws PillException {
        // Arrange
        LocalDate start = LocalDate.now();
        Transaction first = transactionManager.createTransaction(
                "Aspirin", 100, null,
                Transaction.TransactionType.INCOMING, "First", null);
        Transaction second = transactionManager.createTransaction(
                "Bandage", 50, null,
                Transaction.TransactionType.INCOMING, "Second", null);
        LocalDate end = LocalDate.now();

        // Act
        List<Transaction> transactions = transactionManager.getTransactionHistory(
                first.getTimestamp().toLocalDate(), second.getTimestamp().toLocalDate());

        // Assert
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(first));
        assertTrue(transactions.contains(second));
    }

    @Test
    void getTransactionHistory_exactBoundaryTimes_includesTransactions() throws PillException {
        // Arrange
        Transaction first = transactionManager.createTransaction(
                "First", 100, null,
                Transaction.TransactionType.INCOMING, "Boundary start", null);
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
                "Test", 100, null,
                Transaction.TransactionType.INCOMING, "Test", null);
        LocalDate later = LocalDate.now();
        LocalDate earlier = later.minusDays(1);

        // Act
        List<Transaction> transactions = transactionManager.getTransactionHistory(later, earlier);

        // Assert
        assertTrue(transactions.isEmpty());
    }

    @Test
    void listTransactionHistory_printsFormattedTransactions() throws PillException {
        // Arrange
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(1);
        LocalDate end = today.plusDays(1);

        // First create an incoming transaction to add stock
        transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "First transaction",
                null
        );

        // Create an incoming transaction for Bandage first
        transactionManager.createTransaction(
                "Bandage",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Stock addition",
                null
        );

        // Now we can create an outgoing transaction for Bandage since we have stock
        transactionManager.createTransaction(
                "Bandage",
                50,
                null,
                Transaction.TransactionType.OUTGOING,
                "Second transaction",
                null
        );

        // Clear any previous output
        outContent.reset();

        // Act
        transactionManager.listTransactionHistory(start, end);

        // Assert
        String output = outContent.toString();

        // Verify numbering and content expectations
        assertTrue(output.contains("1. "), "Output should contain first item numbering");
        assertTrue(output.contains("2. "), "Output should contain second item numbering");
        assertTrue(output.contains("3. "), "Output should contain third item numbering");

        // Verify all transaction details are present
        assertTrue(output.contains("Aspirin"), "Output should contain Aspirin");
        assertTrue(output.contains("100"), "Output should contain quantity 100");
        assertTrue(output.contains("INCOMING"), "Output should contain INCOMING type");
        assertTrue(output.contains("First transaction"), "Output should contain first transaction notes");

        assertTrue(output.contains("Bandage"), "Output should contain Bandage");
        assertTrue(output.contains("50"), "Output should contain quantity 50");
        assertTrue(output.contains("OUTGOING"), "Output should contain OUTGOING type");
        assertTrue(output.contains("Second transaction"), "Output should contain second transaction notes");

        // Count the number of transactions
        long transactionCount = output.lines().count();
        assertEquals(3, transactionCount, "Should have exactly 3 transactions listed");
    }

    @Test
    void getTransactionHistory_withinRange_returnsMatchingTransactions() throws PillException {
        // Arrange
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(2);

        Transaction transaction = transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Test transaction",
                null
        );

        // Act
        List<Transaction> result = transactionManager.getTransactionHistory(start, end);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(transaction));
    }

    @Test
    void getTransactionHistory_outsideRange_returnsEmptyList() throws PillException {
        // Arrange
        LocalDate futureStart = LocalDate.now().plusDays(5);
        LocalDate futureEnd = LocalDate.now().plusDays(10);

        transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Test transaction",
                null
        );

        // Act
        List<Transaction> result = transactionManager.getTransactionHistory(futureStart, futureEnd);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getTransactionHistory_exactlyOnStartDate_includesTransaction() throws PillException {
        // Arrange
        LocalDate today = LocalDate.now();

        Transaction transaction = transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Test transaction",
                null
        );

        // Act
        List<Transaction> result = transactionManager.getTransactionHistory(today, today.plusDays(1));

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(transaction));
    }

    @Test
    void getTransactionHistory_exactlyOnEndDate_includesTransaction() throws PillException {
        // Arrange
        LocalDate today = LocalDate.now();

        Transaction transaction = transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Test transaction",
                null
        );

        // Act
        List<Transaction> result = transactionManager.getTransactionHistory(today.minusDays(1), today);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(transaction));
    }

    @Test
    void getTransactionHistory_endDateBeforeStartDate_returnsEmptyList() throws PillException {
        // Arrange
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1);

        transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Test transaction",
                null
        );

        // Act
        List<Transaction> result = transactionManager.getTransactionHistory(start, end);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getTransactionHistory_multipleTransactions_returnsCorrectSubset() throws PillException {
        // Arrange
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        // Create transaction within range
        Transaction inRangeTransaction = transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "In range",
                null
        );

        // Add stock for outgoing transaction
        transactionManager.createTransaction(
                "Bandage",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Setup stock",
                null
        );

        // Create another transaction within range
        Transaction alsoInRangeTransaction = transactionManager.createTransaction(
                "Bandage",
                50,
                null,
                Transaction.TransactionType.OUTGOING,
                "Also in range",
                null
        );

        // Act
        List<Transaction> result = transactionManager.getTransactionHistory(start, end);

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(inRangeTransaction));
        assertTrue(result.contains(alsoInRangeTransaction));
    }

    @Test
    void fulfillOrder_purchaseOrder_createsIncomingTransactionsAndFulfills() throws PillException {
        // Arrange
        ItemMap itemsToOrder = new ItemMap();
        Item item1 = new Item("Aspirin", 100);
        Item item2 = new Item("Bandage", 50);
        itemsToOrder.addItem(item1);
        itemsToOrder.addItem(item2);

        Order purchaseOrder = transactionManager.createOrder(
                Order.OrderType.PURCHASE,
                itemsToOrder,
                "Test purchase order"
        );

        // Act
        transactionManager.fulfillOrder(purchaseOrder);

        // Assert
        assertEquals(Order.OrderStatus.FULFILLED, purchaseOrder.getStatus());
        List<Transaction> transactions = transactionManager.getTransactions();
        assertEquals(2, transactions.size());

        // Verify first transaction
        Transaction firstTransaction = transactions.get(0);
        assertEquals("Aspirin", firstTransaction.getItemName());
        assertEquals(100, firstTransaction.getQuantity());
        assertEquals(Transaction.TransactionType.INCOMING, firstTransaction.getType());
        assertEquals("Order fulfillment", firstTransaction.getNotes());
        assertEquals(purchaseOrder, firstTransaction.getAssociatedOrder());

        // Verify second transaction
        Transaction secondTransaction = transactions.get(1);
        assertEquals("Bandage", secondTransaction.getItemName());
        assertEquals(50, secondTransaction.getQuantity());
        assertEquals(Transaction.TransactionType.INCOMING, secondTransaction.getType());
        assertEquals("Order fulfillment", secondTransaction.getNotes());
        assertEquals(purchaseOrder, secondTransaction.getAssociatedOrder());
    }

    @Test
    void fulfillOrder_dispenseOrder_createsOutgoingTransactionsAndFulfills() throws PillException {
        // Arrange
        // First add stock for items
        transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Setup stock",
                null
        );
        transactionManager.createTransaction(
                "Bandage",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "Setup stock",
                null
        );

        // Clear previous transactions to make verification easier
        outContent.reset();

        // Create dispense order
        ItemMap itemsToDispense = new ItemMap();
        Item item1 = new Item("Aspirin", 50);
        Item item2 = new Item("Bandage", 30);
        itemsToDispense.addItem(item1);
        itemsToDispense.addItem(item2);

        Order dispenseOrder = transactionManager.createOrder(
                Order.OrderType.DISPENSE,
                itemsToDispense,
                "Test dispense order"
        );

        // Act
        transactionManager.fulfillOrder(dispenseOrder);

        assertEquals(Order.OrderStatus.FULFILLED, dispenseOrder.getStatus());
        List<Transaction> orderTransactions = transactionManager.getTransactions()
                .stream().filter(t -> t.getAssociatedOrder() == dispenseOrder)
                .toList();
        assertEquals(2, orderTransactions.size());

        // Verify first transaction
        Transaction firstTransaction = orderTransactions.get(0);
        assertEquals("Aspirin", firstTransaction.getItemName());
        assertEquals(50, firstTransaction.getQuantity());
        assertEquals(Transaction.TransactionType.OUTGOING, firstTransaction.getType());
        assertEquals("Order fulfillment", firstTransaction.getNotes());
        assertEquals(dispenseOrder, firstTransaction.getAssociatedOrder());

        // Verify second transaction
        Transaction secondTransaction = orderTransactions.get(1);
        assertEquals("Bandage", secondTransaction.getItemName());
        assertEquals(30, secondTransaction.getQuantity());
        assertEquals(Transaction.TransactionType.OUTGOING, secondTransaction.getType());
        assertEquals("Order fulfillment", secondTransaction.getNotes());
        assertEquals(dispenseOrder, secondTransaction.getAssociatedOrder());
    }

    @Test
    void fulfillOrder_emptyOrder_completesFulfillment() throws PillException {
        // Arrange
        Order emptyOrder = transactionManager.createOrder(
                Order.OrderType.PURCHASE,
                new ItemMap(),
                "Empty order"
        );

        // Act
        transactionManager.fulfillOrder(emptyOrder);

        // Assert
        assertEquals(Order.OrderStatus.FULFILLED, emptyOrder.getStatus());
        assertTrue(transactionManager.getTransactions().isEmpty());
    }

    @Test
    void createTransaction_withExpiry_createsSuccessfully() throws PillException {
        // Arrange
        String itemName = "Aspirin";
        int quantity = 100;
        LocalDate expiryDate = LocalDate.now().plusMonths(6);

        // Act
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                quantity,
                expiryDate,
                Transaction.TransactionType.INCOMING,
                "Test with expiry",
                null
        );

        // Assert
        assertNotNull(transaction);
        assertEquals(itemName, transaction.getItemName());
        assertEquals(quantity, transaction.getQuantity());
        assertEquals(Transaction.TransactionType.INCOMING, transaction.getType());
        assertEquals("Test with expiry", transaction.getNotes());
        assertNull(transaction.getAssociatedOrder());
    }

    @Test
    void listTransactions_noTransactions_printsNoTransactionsMessage() {
        // Arrange - no setup needed, assuming empty transaction list

        // Act
        transactionManager.listTransactions();

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("No transactions found"));
    }

    @Test
    void listOrders_noOrders_printsNoOrdersMessage() {
        // Arrange - no setup needed, assuming empty order list

        // Act
        transactionManager.listOrders();

        // Assert
        String output = outContent.toString();
        assertTrue(output.contains("No orders recorded..."));
    }

    @Test
    void fulfillOrder_dispenseOrderWithInsufficientStock_throwsException() {
        // Arrange
        ItemMap itemsToDispense = new ItemMap();
        Item item = new Item("Aspirin", 100);  // More than available stock
        itemsToDispense.addItem(item);

        Order dispenseOrder = transactionManager.createOrder(
                Order.OrderType.DISPENSE,
                itemsToDispense,
                "Test dispense order"
        );

        // Act & Assert
        assertThrows(PillException.class, () -> transactionManager.fulfillOrder(dispenseOrder));
    }

    @Test
    void createOrder_emptyNotes_createsOrderWithEmptyNotes() {
        // Arrange
        Order.OrderType type = Order.OrderType.PURCHASE;
        ItemMap itemsToOrder = new ItemMap();

        // Act
        Order order = transactionManager.createOrder(type, itemsToOrder, "");

        // Assert
        assertNotNull(order);
        assertEquals("", order.getNotes());
    }

    @Test
    void listTransactions_printsFormattedTransactions() throws PillException {
        // Arrange
        transactionManager.createTransaction(
                "Aspirin",
                100,
                null,
                Transaction.TransactionType.INCOMING,
                "First transaction",
                null
        );
        transactionManager.createTransaction(
                "Bandage",
                50,
                null,
                Transaction.TransactionType.INCOMING,
                "Second transaction",
                null
        );

        // Clear any previous output
        outContent.reset();

        // Act
        transactionManager.listTransactions();

        // Assert
        String output = outContent.toString();

        // Verify numbering and content expectations
        assertTrue(output.contains("1. "), "Output should contain first item numbering");
        assertTrue(output.contains("2. "), "Output should contain second item numbering");

        // Verify all transaction details are present
        assertTrue(output.contains("Aspirin"), "Output should contain Aspirin");
        assertTrue(output.contains("100"), "Output should contain quantity 100");
        assertTrue(output.contains("INCOMING"), "Output should contain INCOMING type");
        assertTrue(output.contains("First transaction"), "Output should contain first transaction notes");

        assertTrue(output.contains("Bandage"), "Output should contain Bandage");
        assertTrue(output.contains("50"), "Output should contain quantity 50");
        assertTrue(output.contains("INCOMING"), "Output should contain INCOMING type");
        assertTrue(output.contains("Second transaction"), "Output should contain second transaction notes");
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }
}
