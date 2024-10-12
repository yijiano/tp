package seedu.pill.command;

import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.pill.util.ItemList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FindCommandTest{
    @Test
    public void listFindEmptyNotFoundPasses() throws PillException {
        // Initialize test environment
        ItemList itemList = new ItemList();
        FindCommand findCommand = new FindCommand("abc");

        // Declare expected output
        String outputFromAddFoundTasks = "";
        String expectedOutput = outputFromAddFoundTasks + "The inventory is empty."
                + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        findCommand.execute(itemList);

        //Compare output
        String output = outputStream.toString();
        output = output.replace("\r\n", "\n");
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        assertEquals(output, expectedOutput);
    }
    @Test
    public void listFindSimplePasses() throws PillException {
        // Initialize test environment
        ItemList itemList = new ItemList();
        itemList.addItem("Bandage", 20);
        itemList.addItem("Syringe", 10);
        itemList.addItem("Band-aid", 5);
        FindCommand findCommand = new FindCommand("Band");

        // Declare expected output
        String outputFromAddFoundTasks = "Added the following item to the inventory: " + System.lineSeparator() +
                "Bandage: 20 in stock" + System.lineSeparator() +
                "Added the following item to the inventory: " + System.lineSeparator() +
                "Band-aid: 5 in stock" + System.lineSeparator();
        String expectedOutput = outputFromAddFoundTasks + "Listing all items:" + System.lineSeparator() +
                "1. Bandage: 20 in stock" + System.lineSeparator() +
                "2. Band-aid: 5 in stock" + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        findCommand.execute(itemList);

        //Compare output
        String output = outputStream.toString();
        output = output.replace("\r\n", "\n");
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        assertEquals(output, expectedOutput);
    }
    @Test
    public void listFindNotFoundPasses() throws PillException {
        // Initialize test environment
        ItemList itemList = new ItemList();
        itemList.addItem("Bandage", 20);
        itemList.addItem("Syringe", 10);
        itemList.addItem("Band-aid", 5);
        FindCommand findCommand = new FindCommand("abc");

        // Declare expected output
        String outputFromAddFoundTasks = "";
        String expectedOutput = outputFromAddFoundTasks + "The inventory is empty."
                + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        findCommand.execute(itemList);

        //Compare output
        String output = outputStream.toString();
        output = output.replace("\r\n", "\n");
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        assertEquals(output, expectedOutput);
    }
}
