package seedu.pill.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class HelpCommandTest {

    // Will add more Test cases as we go, already missing a few I need to test.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testGeneralHelp() throws PillException {
        HelpCommand helpCommand = new HelpCommand(null, false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("help"));
        assertTrue(output.contains("add"));
        assertTrue(output.contains("delete"));
        assertTrue(output.contains("edit"));
        assertTrue(output.contains("list"));
        assertTrue(output.contains("exit"));
    }

    @Test
    public void testSpecificCommandHelp() throws PillException {
        HelpCommand helpCommand = new HelpCommand("add", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("add:"));
        assertTrue(output.contains("Adds a new item to the inventory"));
    }

    @Test
    public void testVerboseSpecificCommandHelp() throws PillException {
        HelpCommand helpCommand = new HelpCommand("add", true);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("add:"));
        assertTrue(output.contains("Adds a new item to the inventory"));
        assertTrue(output.contains("Usage:"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    public void testUnknownCommand() throws PillException {
        HelpCommand helpCommand = new HelpCommand("unknown", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("Unknown command: unknown"));
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    public void testHelpWithEmptyString() throws PillException {
        HelpCommand helpCommand = new HelpCommand("", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("Available commands:"), "Should show general help for empty string input");
    }

    @Test
    public void testHelpWithWhitespaceOnly() throws PillException {
        HelpCommand helpCommand = new HelpCommand("   ", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("Available commands:"), "Should show general help for whitespace-only input");
    }

    @Test
    public void testHelpWithMixedCase() throws PillException {
        HelpCommand helpCommand = new HelpCommand("AdD", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("add:"), "Should recognize command regardless of case");
    }

    @Test
    public void testHelpWithExtraArguments() throws PillException {
        HelpCommand helpCommand = new HelpCommand("add extra args", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("add:"), "Should ignore extra arguments and show help for 'add'");
    }

    @Test
    public void testAllSpecificCommands() throws PillException {
        String[] commands = {"help", "add", "delete", "edit", "list", "exit"};
        for (String cmd : commands) {
            outContent.reset(); // Clear the output stream
            HelpCommand helpCommand = new HelpCommand(cmd, false);
            ItemMap itemMap = new ItemMap();
            Storage storage = new Storage();

            helpCommand.execute(itemMap, storage);

            String output = outContent.toString().trim();
            assertTrue(output.contains(cmd + ":"), "Should show help for " + cmd + " command");
        }
    }

    @Test
    public void testTypoSuggestionWithNoCloseMatch() throws PillException {
        HelpCommand helpCommand = new HelpCommand("xyz", false);
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();

        helpCommand.execute(itemMap, storage);

        String output = outContent.toString().trim();
        assertTrue(output.contains("Unknown command: xyz"), "Should indicate unknown command");
        assertTrue(output.contains("Available commands:"), "Should list available commands");
        assertFalse(output.contains("Did you mean:"), "Should not suggest a command when there's no close match");
    }
}
