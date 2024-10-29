package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;

import java.time.LocalDate;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for AddItemCommand class.
 */
public class AddItemCommandTest {
    private ItemMap itemMap;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
    }

    @Test
    public void execute_validItemWithoutExpiry_itemAddedToMap() throws PillException {
        // Arrange
        AddItemCommand command = new AddItemCommand("panadol", 20);

        // Act
        command.execute(itemMap, storage);

        // Assert
        TreeSet<Item> items = itemMap.get("panadol");
        assertEquals(1, items.size(), "Should have exactly one item");
        Item addedItem = items.first();
        assertEquals("panadol", addedItem.getName(), "Name should match");
        assertEquals(20, addedItem.getQuantity(), "Quantity should match");
        assertFalse(addedItem.getExpiryDate().isPresent(), "Should not have expiry date");
    }

    @Test
    public void execute_validItemWithExpiry_itemAddedToMap() throws PillException {
        // Arrange
        LocalDate expiryDate = LocalDate.parse("2024-12-01");
        AddItemCommand command = new AddItemCommand("panadol", 10, expiryDate);

        // Act
        command.execute(itemMap, storage);

        // Assert
        TreeSet<Item> items = itemMap.get("panadol");
        assertEquals(1, items.size(), "Should have exactly one item");
        Item addedItem = items.first();
        assertEquals("panadol", addedItem.getName(), "Name should match");
        assertEquals(10, addedItem.getQuantity(), "Quantity should match");
        assertEquals(expiryDate, addedItem.getExpiryDate().get(), "Expiry date should match");
    }

    @Test
    public void isExit_returnsFalse() {
        // Arrange
        AddItemCommand command = new AddItemCommand("panadol", 1);

        // Act & Assert
        assertFalse(command.isExit(), "Should always return false");
    }
}
