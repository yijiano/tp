package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void execute_useNonExistentItem_throwsPillException() {
        // Arrange
        UseItemCommand command = new UseItemCommand("NonExistentItem", 1);

        // Act & Assert
        assertThrows(PillException.class, () -> command.execute(itemMap, storage));
    }

    @Test
    public void isExit_returnsFalse() {
        UseItemCommand command = new UseItemCommand("AnyItem", 1);
        assertFalse(command.isExit());
    }
}
