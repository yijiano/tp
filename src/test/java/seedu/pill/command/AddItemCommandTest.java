package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.exceptions.PillException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddItemCommandTest {

    private ItemMap itemMap;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
    }

    @Test
    public void addCommand_validItemWithoutDate_success() throws PillException {
        AddItemCommand command = new AddItemCommand("panadol", 20, null);

        command.execute(itemMap, storage);

        assertTrue(itemMap.findItem("panadol").size() > 0, "Item should exist in the map");
        assertEquals(20, itemMap.findItem("panadol")
                .get("panadol").first().getQuantity(), "Quantity should match");
        assertNull(itemMap.findItem("panadol")
                .get("panadol").first().getExpiryDate().orElse(null), "Expiry date should be null");
    }

    @Test
    public void addCommand_validItemWithDate_success() throws PillException {
        LocalDate expiryDate = LocalDate.parse("2024-12-01");
        AddItemCommand command = new AddItemCommand("panadol", 10, expiryDate);

        command.execute(itemMap, storage);

        assertTrue(itemMap.findItem("panadol").size() > 0, "Item should exist in the map");
        assertEquals(10, itemMap.findItem("panadol")
                .get("panadol").first().getQuantity(), "Quantity should match");
        assertEquals(expiryDate, itemMap.findItem("panadol")
                .get("panadol").first().getExpiryDate().orElse(null), "Expiry date should match");
    }

    @Test
    public void addCommand_invalidItemName_throwsPillException() {
        AddItemCommand command = new AddItemCommand("", 10, null);

        assertThrows(PillException.class, () -> command.execute(itemMap, storage),
                "Invalid item name should throw exception");
    }

    @Test
    public void addCommand_invalidQuantity_throwsPillException() {
        AddItemCommand command = new AddItemCommand("bandage", -5, null);

        assertThrows(PillException.class, () -> command.execute(itemMap, storage),
                "Invalid quantity should throw exception");
    }
}
