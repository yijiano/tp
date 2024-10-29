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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ExpiredCommandTest {
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
    void expiredCommand_emptyInventory_printsEmptyMessage() throws PillException {
        ExpiredCommand command = new ExpiredCommand();
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items that have expired." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiredCommand_noExpiredItems_printsNoExpiredMessage() throws PillException {
        // Add non-expired item
        LocalDate futureDate = LocalDate.now().plusDays(30);
        Item item = new Item("panadol", 20, futureDate);
        itemMap.addItem(item);
        outputStream.reset();

        // Execute command
        ExpiredCommand command = new ExpiredCommand();
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items that have expired." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiredCommand_hasExpiredItems_listsExpiredItems() throws PillException {
        // Add expired item
        LocalDate expiredDate = LocalDate.now().minusDays(1);
        Item expiredItem = new Item("oldMed", 10, expiredDate);
        itemMap.addItem(expiredItem);

        // Add non-expired item
        LocalDate futureDate = LocalDate.now().plusDays(30);
        Item validItem = new Item("newMed", 20, futureDate);
        itemMap.addItem(validItem);

        outputStream.reset();

        // Execute command
        ExpiredCommand command = new ExpiredCommand();
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that have expired" + System.lineSeparator()
                + "1. oldMed: 10 in stock, expiring: " + expiredDate + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiredCommand_itemsWithNoExpiryDate_ignoresItemsWithNoExpiry() throws PillException {
        // Add item with no expiry
        Item noExpiryItem = new Item("med", 10);
        itemMap.addItem(noExpiryItem);

        // Add expired item
        LocalDate expiredDate = LocalDate.now().minusDays(1);
        Item expiredItem = new Item("oldMed", 20, expiredDate);
        itemMap.addItem(expiredItem);

        outputStream.reset();

        // Execute command
        ExpiredCommand command = new ExpiredCommand();
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that have expired" + System.lineSeparator()
                + "1. oldMed: 20 in stock, expiring: " + expiredDate + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiredCommand_mixedExpiryDates_listsOnlyExpiredItems() throws PillException {
        // Add mix of expired, non-expired, and no expiry items
        LocalDate expiredDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = LocalDate.now().plusDays(1);

        itemMap.addItem(new Item("expiredMed", 10, expiredDate));
        itemMap.addItem(new Item("futureMed", 20, futureDate));
        itemMap.addItem(new Item("noExpiryMed", 30));

        outputStream.reset();

        // Execute command
        ExpiredCommand command = new ExpiredCommand();
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that have expired" + System.lineSeparator()
                + "1. expiredMed: 10 in stock, expiring: " + expiredDate + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiredCommand_sameNameDifferentExpiry_handlesCorrectly() throws PillException {
        // Add same item name with different expiry dates
        LocalDate expiredDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = LocalDate.now().plusDays(1);

        itemMap.addItem(new Item("med", 10, expiredDate));
        itemMap.addItem(new Item("med", 20, futureDate));

        outputStream.reset();

        // Execute command
        ExpiredCommand command = new ExpiredCommand();
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that have expired" + System.lineSeparator()
                + "1. med: 10 in stock, expiring: " + expiredDate + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        ExpiredCommand command = new ExpiredCommand();
        assertFalse(command.isExit());
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
