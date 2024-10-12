package seedu.pill.command;

import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.pill.util.ItemList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ListCommandTest {
    @Test
    public void listCommandEmptyPasses() throws PillException {
        // Initialize test environment
        ItemList itemList = new ItemList();
        ListCommand listCommand = new ListCommand();

        // Declare expected output
        String expectedOutput = "The inventory is empty." + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        listCommand.execute(itemList);

        //Compare output
        String output = outputStream.toString();
        assertEquals(output, expectedOutput);
    }
    @Test
    public void listCommandSimplePasses() throws PillException {
        // Initialize test environment
        ItemList itemList = new ItemList();
        itemList.addItem("Bandage", 20);
        itemList.addItem("Syringe", 10);
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
        listCommand.execute(itemList);

        //Compare output
        String output = outputStream.toString();
        assertEquals(output, expectedOutput);
    }
}
