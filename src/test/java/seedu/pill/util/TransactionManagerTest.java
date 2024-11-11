package seedu.pill.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TreeSet;

class TransactionManagerTest {
    private TransactionManager transactionManager;
    private ItemMap itemMap;
    private static final String VALID_ITEM_NAME = "Aspirin";
    private static final int VALID_QUANTITY = 100;
    private static final String VALID_NOTES = "Test notes";

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
    void fulfillOrder_alreadyFulfilledOrder_throwsException() throws PillException {
        Order order = transactionManager.createOrder(Order.OrderType.PURCHASE, new ItemMap(), VALID_NOTES);
        order.fulfill();

        assertThrows(PillException.class, () -> transactionManager.fulfillOrder(order));
    }

    // Tests for Transaction History
    @Test
    void getTransactionHistory_validTimeRange_returnsCorrectTransactions() throws PillException {
        LocalDateTime start = LocalDateTime.now();
        Transaction transaction1 = transactionManager.createTransaction(
                "Item1", 10, Transaction.TransactionType.INCOMING, "First", null);
        Transaction transaction2 = transactionManager.createTransaction(
                "Item2", 20, Transaction.TransactionType.INCOMING, "Second", null);
        LocalDateTime end = LocalDateTime.now();

        List<Transaction> history = transactionManager.getTransactionHistory(start, end);

        assertEquals(2, history.size());
        assertTrue(history.contains(transaction1));
        assertTrue(history.contains(transaction2));
    }

    @Test
    void getTransactionHistory_emptyTimeRange_returnsEmptyList() {
        LocalDateTime now = LocalDateTime.now();
        List<Transaction> history = transactionManager.getTransactionHistory(now, now);
        assertTrue(history.isEmpty());
    }

    @Test
    void getTransactionHistory_endBeforeStart_returnsEmptyList() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earlier = now.minus(1, ChronoUnit.HOURS);
        List<Transaction> history = transactionManager.getTransactionHistory(now, earlier);
        assertTrue(history.isEmpty());
    }

    @Test
    void getItemTransactions_nonexistentItem_returnsEmptyList() {
        List<Transaction> transactions = transactionManager.getItemTransactions("NonexistentItem");
        assertTrue(transactions.isEmpty());
    }
}
