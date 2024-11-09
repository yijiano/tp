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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockCheckCommandTest {
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
    void stockCheck_emptyInventory_printsInfoMessage() throws PillException {
        StockCheckCommand command = new StockCheckCommand("10");
        command.execute(itemMap, storage);

        assertEquals("", outputStream.toString(),
                "Empty inventory should not produce output");
    }

    @Test
    void stockCheck_noItemsBelowThreshold_printsInfoMessage() throws PillException {
        // Setup
        itemMap.addItem(new Item("med1", 20));
        itemMap.addItem(new Item("med2", 30));
        outputStream.reset();

        // Execute
        StockCheckCommand command = new StockCheckCommand("10");
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items that have quantity less than or equal to 10:"
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void stockCheck_itemsBelowThreshold_listsItems() throws PillException {
        // Setup
        itemMap.addItem(new Item("lowMed", 5));
        itemMap.addItem(new Item("highMed", 20));
        outputStream.reset();

        // Execute
        StockCheckCommand command = new StockCheckCommand("10");
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that need to be restocked (less than or equal to 10):"
                + System.lineSeparator()
                + "1. lowMed: 5 in stock" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void stockCheck_multipleItemsBelowThreshold_listsAllItems() throws PillException {
        // Setup
        itemMap.addItem(new Item("med1", 5));
        itemMap.addItem(new Item("med2", 8));
        itemMap.addItem(new Item("med3", 20));
        outputStream.reset();

        // Execute
        StockCheckCommand command = new StockCheckCommand("10");
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that need to be restocked (less than or equal to 10):"
                + System.lineSeparator()
                + "1. med1: 5 in stock" + System.lineSeparator()
                + "2. med2: 8 in stock" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void stockCheck_itemsEqualToThreshold_included() throws PillException {
        // Setup
        itemMap.addItem(new Item("medAtThreshold", 10));
        outputStream.reset();

        // Execute
        StockCheckCommand command = new StockCheckCommand("10");
        command.execute(itemMap, storage);

        String expectedOutput = "Listing all items that need to be restocked (less than or equal to 10):"
                + System.lineSeparator()
                + "1. medAtThreshold: 10 in stock" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void stockCheck_nonNumericThreshold_throwsException() {
        assertThrows(NumberFormatException.class,
                () -> new StockCheckCommand("abc"));
    }

    @Test
    void stockCheck_zeroThreshold_listsNoItems() throws PillException {
        // Setup
        itemMap.addItem(new Item("med", 5));
        outputStream.reset();

        // Execute
        StockCheckCommand command = new StockCheckCommand("0");
        command.execute(itemMap, storage);

        String expectedOutput = "There are no items that have quantity less than or equal to 0:"
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        StockCheckCommand command = new StockCheckCommand("10");
        assertFalse(command.isExit());
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
