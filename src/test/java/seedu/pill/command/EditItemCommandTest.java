package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditItemCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @Test
    void editCommand_existingItemWithoutExpiry_success() throws PillException {
        // Setup
        Item item = new Item("panadol", 20);
        itemMap.addItem(item);
        outputStream.reset();

        // Execute
        EditItemCommand command = new EditItemCommand("panadol", 30);
        command.execute(itemMap, storage);

        // Verify
        assertEquals(30, itemMap.get("panadol").first().getQuantity(),
                "Quantity should be updated");
        String expectedOutput = "Edited item: panadol: 30 in stock" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void editCommand_existingItemWithExpiry_success() throws PillException {
        // Setup
        LocalDate expiryDate = LocalDate.parse("2024-12-01");
        Item item = new Item("panadol", 20, expiryDate);
        itemMap.addItem(item);
        outputStream.reset();

        // Execute
        EditItemCommand command = new EditItemCommand("panadol", 30, expiryDate);
        command.execute(itemMap, storage);

        // Verify
        assertEquals(30, itemMap.get("panadol").first().getQuantity(),
                "Quantity should be updated");
        String expectedOutput = "Edited item: panadol: 30 in stock, expiring: 2024-12-01"
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void editCommand_nonexistentItem_printsError() throws PillException {
        EditItemCommand command = new EditItemCommand("nonexistent", 30);
        command.execute(itemMap, storage);

        String expectedOutput = "Item not found: nonexistent" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void editCommand_multipleItemsDifferentExpiry_updatesCorrectItem() throws PillException {
        // Setup
        LocalDate date1 = LocalDate.parse("2024-12-01");
        LocalDate date2 = LocalDate.parse("2025-12-01");
        itemMap.addItem(new Item("panadol", 20, date1));
        itemMap.addItem(new Item("panadol", 30, date2));
        outputStream.reset();

        // Execute
        EditItemCommand command = new EditItemCommand("panadol", 40, date1);
        command.execute(itemMap, storage);

        // Verify
        TreeSet<Item> items = itemMap.get("panadol");
        boolean foundUpdatedItem = false;
        for (Item item : items) {
            if (item.getExpiryDate().get().equals(date1)) {
                assertEquals(40, item.getQuantity(), "Item quantity should be updated");
                foundUpdatedItem = true;
            } else {
                assertEquals(30, item.getQuantity(), "Other item should remain unchanged");
            }
        }
        assertTrue(foundUpdatedItem, "Updated item should be found");
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        EditItemCommand command = new EditItemCommand("test", 1);
        assertFalse(command.isExit());
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
