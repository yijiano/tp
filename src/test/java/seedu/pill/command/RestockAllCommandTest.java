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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestockAllCommandTest {
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
    public void execute_noItemsBelowThreshold_printsNoChanges() throws PillException {
        itemMap.addItemSilent(new Item("Paracetamol", 20, LocalDate.now(), 5.0, 7.0));
        itemMap.addItemSilent(new Item("Aspirin", 15, LocalDate.now(), 4.0, 6.0));

        RestockAllCommand command = new RestockAllCommand(10);
        command.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Total Restock Cost for all items below threshold 10: $0.00"));
    }

    @Test
    public void execute_calculatesCorrectCosts() throws PillException {
        itemMap.addItemSilent(new Item("Paracetamol", 5, LocalDate.now(), 5.0, 7.0));
        // Need to restock 5 units at $5.0 each = $25.0

        RestockAllCommand command = new RestockAllCommand(10);
        command.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Restock Cost: $25.00"));
        assertTrue(output.contains("Total Restock Cost for all items below threshold 10: $25.00"));
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        RestockAllCommand command = new RestockAllCommand(10);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
