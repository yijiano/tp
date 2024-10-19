package seedu.pill.command;

import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ListCommandTest {
    @Test
    public void listCommandEmptyPasses() throws PillException {
        // Initialize test environment
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        ListCommand listCommand = new ListCommand();

        // Declare expected output
        String expectedOutput = "The inventory is empty." + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        listCommand.execute(itemMap, storage);

        //Compare output
        String output = outputStream.toString();
        assertEquals(expectedOutput, output);
    }
    @Test
    public void listCommandSimplePasses() throws PillException {
        // Initialize test environment
        ItemMap itemMap = new ItemMap();

        Item item1 = new Item("Bandage", 20);
        Item item2 = new Item("Syringe", 10);

        itemMap.addItem(item1);
        itemMap.addItem(item2);

        Storage storage = new Storage();
        ListCommand listCommand = new ListCommand();

        // Declare expected output
        String expectedOutput = "Listing all items:" + System.lineSeparator() +
                "1. Bandage: 20 in stock" + System.lineSeparator() +
                "2. Syringe: 10 in stock" + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        listCommand.execute(itemMap, storage);

        //Compare output
        String output = outputStream.toString();
        assertEquals(expectedOutput, output);
    }
}
