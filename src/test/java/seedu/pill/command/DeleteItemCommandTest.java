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

class DeleteItemCommandTest {
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
    public void deleteCommand_nonexistentItem_printsError() throws PillException {
        DeleteItemCommand command = new DeleteItemCommand("nonexistent");
        command.execute(itemMap, storage);

        String expectedOutput = "Item not found: nonexistent" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void deleteCommand_multipleItemsWithDifferentExpiry_deletesCorrectItem() throws PillException {
        // Setup
        LocalDate date1 = LocalDate.parse("2024-12-01");
        LocalDate date2 = LocalDate.parse("2025-12-01");
        itemMap.addItem(new Item("panadol", 20, date1));
        itemMap.addItem(new Item("panadol", 30, date2));
        outputStream.reset();

        // Execute
        DeleteItemCommand command = new DeleteItemCommand("panadol", date1);
        command.execute(itemMap, storage);

        // Verify
        TreeSet<Item> remainingItems = itemMap.get("panadol");
        assertEquals(1, remainingItems.size(), "Should have one item remaining");
        assertEquals(date2, remainingItems.first().getExpiryDate().get(),
                "Item with incorrect expiry date was deleted");
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        DeleteItemCommand command = new DeleteItemCommand("test");
        assertFalse(command.isExit());
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
