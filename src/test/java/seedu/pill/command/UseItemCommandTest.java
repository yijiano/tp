package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UseItemCommand class.
 */
public class UseItemCommandTest {
    private ItemMap itemMap;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
    }

    public void execute_usePartialQuantityFromSingleItem_success() throws PillException {
        // Arrange
        Item item = new Item("Panadol", 10, LocalDate.now().plusDays(30));
        itemMap.addItem(item);
        UseItemCommand command = new UseItemCommand("Panadol", 3);

        // Act
        command.execute(itemMap, storage);

        // Assert
        assertEquals(7, itemMap.stockCount("Panadol"));
    }

    @Test
    public void execute_useExactQuantityFromSingleItem_success() throws PillException {
        // Arrange
        Item item = new Item("Panadol", 5, LocalDate.now().plusDays(30));
        itemMap.addItem(item);
        UseItemCommand command = new UseItemCommand("Panadol", 5);

        // Act
        command.execute(itemMap, storage);

        // Assert
        assertEquals(0, itemMap.stockCount("Panadol"));
        assertThrows(PillException.class, () -> itemMap.getSoonestExpiringItem("Panadol"));
    }

    @Test
    public void execute_useQuantityAcrossMultipleItems_success() throws PillException {
        // Arrange
        LocalDate earlierDate = LocalDate.now().plusDays(10);
        LocalDate laterDate = LocalDate.now().plusDays(30);
        Item item1 = new Item("Vitamin C", 5, earlierDate);  // Earlier expiry
        Item item2 = new Item("Vitamin C", 5, laterDate);    // Later expiry
        itemMap.addItem(item1);
        itemMap.addItem(item2);
        UseItemCommand command = new UseItemCommand("Vitamin C", 7);

        // Act
        command.execute(itemMap, storage);

        // Assert
        assertEquals(3, itemMap.stockCount("Vitamin C"));
        // Verify remaining item is the one with later expiry
        assertEquals(laterDate, itemMap.getSoonestExpiringItem("Vitamin C").getExpiryDate().get());
    }

    @Test
    public void execute_useNonExistentItem_throwsPillException() {
        // Arrange
        UseItemCommand command = new UseItemCommand("NonExistentItem", 1);

        // Act & Assert
        assertThrows(PillException.class, () -> command.execute(itemMap, storage));
    }

    @Test
    public void execute_useMoreThanAvailableQuantity_throwsPillException() {
        // Arrange
        Item item = new Item("Panadol", 5, LocalDate.now().plusDays(30));
        itemMap.addItem(item);
        UseItemCommand command = new UseItemCommand("Panadol", 10);

        // Act & Assert
        assertThrows(PillException.class, () -> command.execute(itemMap, storage));
        assertEquals(5, itemMap.stockCount("Panadol"));  // Verify quantity unchanged
    }

    @Test
    public void execute_useItemWithNoExpiryDate_success() throws PillException {
        // Arrange
        Item item = new Item("Generic Pills", 10);  // No expiry date
        itemMap.addItem(item);
        UseItemCommand command = new UseItemCommand("Generic Pills", 4);

        // Act
        command.execute(itemMap, storage);

        // Assert
        assertEquals(6, itemMap.stockCount("Generic Pills"));
    }

    @Test
    public void execute_useMultipleItemsInExpiryOrder_success() throws PillException {
        // Arrange
        LocalDate date1 = LocalDate.now().plusDays(10);
        LocalDate date2 = LocalDate.now().plusDays(20);
        LocalDate date3 = LocalDate.now().plusDays(30);

        itemMap.addItem(new Item("Medicine", 3, date2));  // Added out of order
        itemMap.addItem(new Item("Medicine", 2, date3));
        itemMap.addItem(new Item("Medicine", 4, date1));  // Should be used first

        UseItemCommand command = new UseItemCommand("Medicine", 6);

        // Act
        command.execute(itemMap, storage);

        // Assert
        assertEquals(3, itemMap.stockCount("Medicine"));
        assertEquals(date2, itemMap.getSoonestExpiringItem("Medicine").getExpiryDate().get());
    }

    @Test
    public void isExit_returnsFalse() {
        UseItemCommand command = new UseItemCommand("AnyItem", 1);
        assertFalse(command.isExit());
    }
}
