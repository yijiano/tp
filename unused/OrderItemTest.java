//@@author philip1304-unused
// Same reason given in OrderItem.java
package seedu.pill.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Optional;

class OrderItemTest {

    @Test
    void constructor_basicConstructor_createsOrderItemSuccessfully() {
        // Arrange & Act
        OrderItem item = new OrderItem("Aspirin", 100);

        // Assert
        assertEquals("Aspirin", item.getItemName());
        assertEquals(100, item.getQuantity());
        assertEquals(Optional.empty(), item.getExpiryDate());
        assertEquals(0.0, item.getUnitPrice());
    }

    @Test
    void constructor_fullConstructor_createsOrderItemSuccessfully() {
        // Arrange
        LocalDate expiryDate = LocalDate.now().plusYears(1);

        // Act
        OrderItem item = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        assertEquals("Aspirin", item.getItemName());
        assertEquals(100, item.getQuantity());
        assertEquals(Optional.of(expiryDate), item.getExpiryDate());
        assertEquals(5.99, item.getUnitPrice());
    }

    @Test
    void constructor_nullItemName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem(null, 100));
    }

    @Test
    void constructor_emptyItemName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("", 100));
    }

    @Test
    void constructor_blankItemName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("   ", 100));
    }

    @Test
    void constructor_negativeQuantity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("Aspirin", -1));
    }

    @Test
    void constructor_negativeUnitPrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("Aspirin", 100, null, -1.0));
    }

    @Test
    void getTotalPrice_calculatesCorrectly() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100, null, 5.99);

        // Act
        double totalPrice = item.getTotalPrice();

        // Assert
        assertEquals(599.0, totalPrice, 0.001);
    }

    @Test
    void getTotalPrice_withZeroQuantity_returnsZero() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 0, null, 5.99);

        // Act
        double totalPrice = item.getTotalPrice();

        // Assert
        assertEquals(0.0, totalPrice, 0.001);
    }

    @Test
    void getTotalPrice_withZeroUnitPrice_returnsZero() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100, null, 0.0);

        // Act
        double totalPrice = item.getTotalPrice();

        // Assert
        assertEquals(0.0, totalPrice, 0.001);
    }

    @Test
    void toString_withoutExpiryAndPrice_returnsBasicFormat() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100);

        // Act
        String result = item.toString();

        // Assert
        assertEquals("Aspirin: 100 units", result);
    }

    @Test
    void toString_withExpiryDate_includesExpiry() {
        // Arrange
        LocalDate expiryDate = LocalDate.of(2024, 12, 31);
        OrderItem item = new OrderItem("Aspirin", 100, expiryDate, 0.0);

        // Act
        String result = item.toString();

        // Assert
        assertTrue(result.contains("expires: 2024-12-31"));
    }

    @Test
    void toString_withUnitPrice_includesPricing() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100, null, 5.99);

        // Act
        String result = item.toString();

        // Assert
        assertTrue(result.contains("unit price: $5.99"));
        assertTrue(result.contains("total: $599.00"));
    }

    @Test
    void equals_sameValues_returnsTrue() {
        // Arrange
        LocalDate expiryDate = LocalDate.now().plusYears(1);
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        assertEquals(item1, item2);
    }

    @Test
    void equals_differentValues_returnsFalse() {
        // Arrange
        OrderItem item1 = new OrderItem("Aspirin", 100, null, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 200, null, 5.99);
        OrderItem item3 = new OrderItem("Paracetamol", 100, null, 5.99);
        OrderItem item4 = new OrderItem("Aspirin", 100, null, 6.99);

        // Assert
        assertNotEquals(item1, item2); // Different quantity
        assertNotEquals(item1, item3); // Different name
        assertNotEquals(item1, item4); // Different price
    }

    @Test
    void equals_null_returnsFalse() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100);

        // Assert
        assertNotEquals(null, item);
    }

    @Test
    void equals_differentClass_returnsFalse() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100);
        String other = "Aspirin";

        // Assert
        assertNotEquals(other, item);
    }

    @Test
    void hashCode_sameValues_returnsSameHash() {
        // Arrange
        LocalDate expiryDate = LocalDate.now().plusYears(1);
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void hashCode_differentValues_returnsDifferentHash() {
        // Arrange
        OrderItem item1 = new OrderItem("Aspirin", 100, null, 5.99);
        OrderItem item2 = new OrderItem("Paracetamol", 100, null, 5.99);

        // Assert
        assertNotEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100, LocalDate.now(), 5.99);

        // Assert
        assertTrue(item.equals(item)); // Tests the this == obj condition
    }

    @Test
    void equals_nullObject_returnsFalse() {
        // Arrange
        OrderItem item = new OrderItem("Aspirin", 100);

        // Assert
        assertFalse(item.equals(null)); // Tests null comparison
    }

    @Test
    void equals_allFieldsSame_returnsTrue() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        assertTrue(item1.equals(item2)); // Tests all fields equality
        assertTrue(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_differentItemName_returnsFalse() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Paracetamol", 100, expiryDate, 5.99);

        // Assert
        assertFalse(item1.equals(item2));
        assertFalse(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_differentQuantity_returnsFalse() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 200, expiryDate, 5.99);

        // Assert
        assertFalse(item1.equals(item2));
        assertFalse(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_differentExpiryDate_returnsFalse() {
        // Arrange
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);
        OrderItem item1 = new OrderItem("Aspirin", 100, date1, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, date2, 5.99);

        // Assert
        assertFalse(item1.equals(item2));
        assertFalse(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_differentUnitPrice_returnsFalse() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 6.99);

        // Assert
        assertFalse(item1.equals(item2));
        assertFalse(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_sameValuesWithNullExpiryDate_returnsTrue() {
        // Arrange
        OrderItem item1 = new OrderItem("Aspirin", 100, null, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, null, 5.99);

        // Assert
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_oneNullExpiryDate_returnsFalse() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, null, 5.99);

        // Assert
        assertFalse(item1.equals(item2));
        assertFalse(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_veryCloseUnitPrices_handlesDoublePrecision() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        // Use exactly the same double value
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1)); // Tests symmetry
    }

    @Test
    void equals_transitivityProperty_maintainsConsistency() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item3 = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item3));
        assertTrue(item1.equals(item3)); // Tests transitivity
    }

    @Test
    void equals_consistencyWithHashCode_maintainsContract() {
        // Arrange
        LocalDate expiryDate = LocalDate.now();
        OrderItem item1 = new OrderItem("Aspirin", 100, expiryDate, 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, expiryDate, 5.99);

        // Assert
        // If two objects are equal, their hash codes must be equal
        assertTrue(item1.equals(item2));
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void equals_multipleCallsSameObject_consistentResults() {
        // Arrange
        OrderItem item1 = new OrderItem("Aspirin", 100, LocalDate.now(), 5.99);
        OrderItem item2 = new OrderItem("Aspirin", 100, LocalDate.now(), 5.99);

        // Assert
        // Multiple calls should return consistent results
        boolean firstCall = item1.equals(item2);
        boolean secondCall = item1.equals(item2);
        boolean thirdCall = item1.equals(item2);

        assertEquals(firstCall, secondCall);
        assertEquals(secondCall, thirdCall);
    }
}
