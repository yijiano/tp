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

class ExpiringCommandTest {
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
    void expiringCommand_emptyInventory_printsEmptyMessage() throws PillException {
        LocalDate cutOffDate = LocalDate.now().plusDays(30);
        ExpiringCommand command = new ExpiringCommand(cutOffDate);
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items expiring before " + cutOffDate + "."
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiringCommand_noExpiringItems_printsNoItemsMessage() throws PillException {
        // Setup
        LocalDate cutOffDate = LocalDate.now().plusDays(30);
        LocalDate laterDate = LocalDate.now().plusDays(60);
        itemMap.addItem(new Item("futureMed", 20, laterDate));
        outputStream.reset();

        // Execute
        ExpiringCommand command = new ExpiringCommand(cutOffDate);
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items expiring before " + cutOffDate + "."
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiringCommand_hasExpiringItems_listsItems() throws PillException {
        // Setup
        LocalDate cutOffDate = LocalDate.now().plusDays(30);
        LocalDate expiringDate = LocalDate.now().plusDays(20);
        itemMap.addItem(new Item("expiringMed", 10, expiringDate));
        outputStream.reset();

        // Execute
        ExpiringCommand command = new ExpiringCommand(cutOffDate);
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items expiring before " + cutOffDate + System.lineSeparator()
                + "1. expiringMed: 10 in stock, expiring: " + expiringDate + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiringCommand_multipleExpiringItems_listsAllItems() throws PillException {
        // Setup
        LocalDate cutOffDate = LocalDate.now().plusDays(30);
        LocalDate date1 = LocalDate.now().plusDays(10);
        LocalDate date2 = LocalDate.now().plusDays(20);
        itemMap.addItem(new Item("med1", 10, date1));
        itemMap.addItem(new Item("med2", 20, date2));
        outputStream.reset();

        // Execute
        ExpiringCommand command = new ExpiringCommand(cutOffDate);
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items expiring before " + cutOffDate + System.lineSeparator()
                + "1. med1: 10 in stock, expiring: " + date1 + System.lineSeparator()
                + "2. med2: 20 in stock, expiring: " + date2 + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiringCommand_mixedExpiryDates_listsOnlyExpiringItems() throws PillException {
        // Setup
        LocalDate cutOffDate = LocalDate.now().plusDays(30);
        LocalDate expiringDate = LocalDate.now().plusDays(20);
        LocalDate nonExpiringDate = LocalDate.now().plusDays(40);
        itemMap.addItem(new Item("expiringMed", 10, expiringDate));
        itemMap.addItem(new Item("nonExpiringMed", 20, nonExpiringDate));
        itemMap.addItem(new Item("noExpiryMed", 30));
        outputStream.reset();

        // Execute
        ExpiringCommand command = new ExpiringCommand(cutOffDate);
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items expiring before " + cutOffDate + System.lineSeparator()
                + "1. expiringMed: 10 in stock, expiring: " + expiringDate + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void expiringCommand_itemsExpiringOnCutoffDate_notIncluded() throws PillException {
        // Setup
        LocalDate cutOffDate = LocalDate.now().plusDays(30);
        itemMap.addItem(new Item("medOnCutoff", 15, cutOffDate));
        outputStream.reset();

        // Execute
        ExpiringCommand command = new ExpiringCommand(cutOffDate);
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items expiring before " + cutOffDate + "."
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        ExpiringCommand command = new ExpiringCommand(LocalDate.now());
        assertFalse(command.isExit());
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
