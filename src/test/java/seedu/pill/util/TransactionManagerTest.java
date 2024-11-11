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
    private TransactionManager transactionManager;
    private ItemMap itemMap;

    @BeforeEach
    void setUp() {
        itemMap = new ItemMap();
        transactionManager = new TransactionManager(itemMap);
    }
    @Test
    void createTransaction_insufficientStock_throwsException() {
        // Arrange
        String itemName = "Aspirin";
        int initialQuantity = 50;
        int decreaseQuantity = 100;

        assertThrows(PillException.class, () -> {
            transactionManager.createTransaction(
                    itemName,
                    decreaseQuantity,
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

        // Act
        Transaction transaction = transactionManager.createTransaction(
                itemName,
                quantity,
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

        // Assert
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(first));
        assertTrue(transactions.contains(second));
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
