package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Visualizer;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VisualizePriceCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private Visualizer visualizer;
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
        visualizer = new Visualizer(new ArrayList<>());
    }

    @Test
    public void execute_emptyItemMap_throwsException() {
        VisualizePriceCommand command = new VisualizePriceCommand(visualizer);

        assertThrows(PillException.class, () -> {
            command.execute(itemMap, storage);
        });
    }

    @Test
    public void execute_itemsWithNoPrice_throwsException() {
        itemMap.addItemSilent(new Item("Paracetamol", 10));
        VisualizePriceCommand command = new VisualizePriceCommand(visualizer);

        assertThrows(PillException.class, () -> {
            command.execute(itemMap, storage);
        });
    }

    @Test
    public void execute_validItems_executesSuccessfully() throws PillException {
        Item item = new Item("Paracetamol", 10, LocalDate.now(), 0, 7.0);
        itemMap.addItemSilent(item);
        VisualizePriceCommand command = new VisualizePriceCommand(visualizer);

        command.execute(itemMap, storage);
        // Note: Can't test actual chart generation as it's a GUI operation
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        VisualizePriceCommand command = new VisualizePriceCommand(visualizer);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}