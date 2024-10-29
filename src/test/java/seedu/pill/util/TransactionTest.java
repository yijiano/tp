package seedu.pill.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class TransactionTest {

    @Test
    void constructor_withValidInputs_createsTransaction() {
        // Arrange
        String itemName = "Aspirin";
        int quantity = 100;
        Transaction.TransactionType type = Transaction.TransactionType.INCOMING;
        String notes = "Test transaction";
        Order associatedOrder = new Order(Order.OrderType.PURCHASE, "Test order");

        // Act
        Transaction transaction = new Transaction(itemName, quantity, type, notes, associatedOrder);

        // Assert
        assertNotNull(transaction.getId());
        assertEquals(itemName, transaction.getItemName());
        assertEquals(quantity, transaction.getQuantity());
        assertEquals(type, transaction.getType());
        assertEquals(notes, transaction.getNotes());
        assertEquals(associatedOrder, transaction.getAssociatedOrder());

        // Verify timestamp is recent (within last second)
        LocalDateTime now = LocalDateTime.now();
        long timeDiff = ChronoUnit.SECONDS.between(transaction.getTimestamp(), now);
        assertTrue(Math.abs(timeDiff) <= 1);
    }

    @Test
    void constructor_withNullOrder_createsTransactionWithoutOrder() {
        // Arrange
        String itemName = "Bandages";
        int quantity = 50;
        Transaction.TransactionType type = Transaction.TransactionType.OUTGOING;
        String notes = "Direct transaction";

        // Act
        Transaction transaction = new Transaction(itemName, quantity, type, notes, null);

        // Assert
        assertNotNull(transaction.getId());
        assertEquals(itemName, transaction.getItemName());
        assertEquals(quantity, transaction.getQuantity());
        assertEquals(type, transaction.getType());
        assertEquals(notes, transaction.getNotes());
        assertNull(transaction.getAssociatedOrder());
    }

    @Test
    void getId_returnsUniqueIds() {
        // Arrange
        Transaction transaction1 = new Transaction("Item1", 10,
                Transaction.TransactionType.INCOMING, "Note1", null);
        Transaction transaction2 = new Transaction("Item2", 20,
                Transaction.TransactionType.OUTGOING, "Note2", null);

        // Act & Assert
        assertNotEquals(transaction1.getId(), transaction2.getId());
    }

    @Test
    void getTimestamp_returnsCorrectTimestamp() {
        // Arrange
        LocalDateTime beforeCreation = LocalDateTime.now();
        Transaction transaction = new Transaction("Item", 10,
                Transaction.TransactionType.INCOMING, "Note", null);
        LocalDateTime afterCreation = LocalDateTime.now();

        // Act
        LocalDateTime timestamp = transaction.getTimestamp();

        // Assert
        assertTrue(timestamp.isEqual(beforeCreation) || timestamp.isAfter(beforeCreation));
        assertTrue(timestamp.isEqual(afterCreation) || timestamp.isBefore(afterCreation));
    }

    @Test
    void toString_withoutOrder_returnsCorrectFormat() {
        // Arrange
        Transaction transaction = new Transaction("TestItem", 10,
                Transaction.TransactionType.INCOMING, "Test note", null);

        // Act
        String result = transaction.toString();

        // Assert
        assertTrue(result.contains("TestItem"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("INCOMING"));
        assertTrue(result.contains("Test note"));
        assertFalse(result.contains("Order:"));
    }

    @Test
    void toString_withOrder_returnsCorrectFormat() {
        // Arrange
        Order order = new Order(Order.OrderType.PURCHASE, "Test order");
        Transaction transaction = new Transaction("TestItem", 10,
                Transaction.TransactionType.INCOMING, "Test note", order);

        // Act
        String result = transaction.toString();

        // Assert
        assertTrue(result.contains("TestItem"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("INCOMING"));
        assertTrue(result.contains("Test note"));
        assertTrue(result.contains("Order: " + order.getId()));
    }

    @Test
    void transactionTypes_haveCorrectValues() {
        // Assert
        assertEquals(2, Transaction.TransactionType.values().length);
        assertArrayEquals(
            new Transaction.TransactionType[] {
                Transaction.TransactionType.INCOMING,
                Transaction.TransactionType.OUTGOING
            },
            Transaction.TransactionType.values()
        );
    }

    @Test
    void multipleCalls_generateDifferentIds() {
        // Arrange & Act
        UUID[] ids = new UUID[100];
        for (int i = 0; i < 100; i++) {
            Transaction transaction = new Transaction("Item", 1,
                    Transaction.TransactionType.INCOMING, "Note", null);
            ids[i] = transaction.getId();
        }

        // Assert
        // Check that all IDs are unique
        for (int i = 0; i < ids.length; i++) {
            for (int j = i + 1; j < ids.length; j++) {
                assertNotEquals(ids[i], ids[j],
                        "Generated UUIDs should be unique");
            }
        }
    }

    @Test
    void transaction_withZeroQuantity_createsSuccessfully() {
        // Arrange & Act
        Transaction transaction = new Transaction("TestItem", 0,
                Transaction.TransactionType.INCOMING, "Zero quantity test", null);

        // Assert
        assertEquals(0, transaction.getQuantity());
    }

    @Test
    void transaction_withEmptyNotes_createsSuccessfully() {
        // Arrange & Act
        Transaction transaction = new Transaction("TestItem", 10,
                Transaction.TransactionType.INCOMING, "", null);

        // Assert
        assertEquals("", transaction.getNotes());
    }
}
