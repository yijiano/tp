package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ListCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    // Existing test cases
    @Test
    public void listCommandEmptyPasses() throws PillException {
        ListCommand listCommand = new ListCommand();

        listCommand.execute(itemMap, storage);

        String expectedOutput = "The inventory is empty." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        ListCommand command = new ListCommand();
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
