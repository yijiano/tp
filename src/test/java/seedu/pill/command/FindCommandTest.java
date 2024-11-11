package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for FindCommand
 */
public class FindCommandTest {
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

    @Test
    public void execute_emptyItemMap_throwsException() {
        FindCommand findCommand = new FindCommand("panadol");

        PillException exception = assertThrows(PillException.class, () -> {
            findCommand.execute(itemMap, storage);
        });

        assertEquals("Item not found...", exception.getMessage());
    }

    @Test
    public void execute_exactMatch_findsItem() throws PillException {
        itemMap.addItemSilent(new Item("panadol", 5));
        FindCommand findCommand = new FindCommand("panadol");

        findCommand.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("panadol"));
    }

    @Test
    public void execute_partialMatch_findsItem() throws PillException {
        itemMap.addItemSilent(new Item("panadol extra", 5));
        FindCommand findCommand = new FindCommand("panadol");

        findCommand.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("panadol extra"));
    }

    @Test
    public void execute_caseInsensitiveMatch_findsItem() throws PillException {
        itemMap.addItemSilent(new Item("Panadol", 5));
        FindCommand findCommand = new FindCommand("panadol");

        findCommand.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Panadol"));
    }

    @Test
    public void execute_multipleMatches_findsAllItems() throws PillException {
        itemMap.addItemSilent(new Item("panadol extra", 5));
        itemMap.addItemSilent(new Item("panadol active", 3));
        FindCommand findCommand = new FindCommand("panadol");

        findCommand.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("panadol extra"));
        assertTrue(output.contains("panadol active"));
    }

    @Test
    public void execute_noMatch_throwsException() {
        itemMap.addItemSilent(new Item("aspirin", 5));
        FindCommand findCommand = new FindCommand("panadol");

        PillException exception = assertThrows(PillException.class, () -> {
            findCommand.execute(itemMap, storage);
        });

        assertEquals("Item not found...", exception.getMessage());
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        FindCommand command = new FindCommand("test");
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
