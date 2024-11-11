package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Visualizer;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VisualizeCostPriceCommandTest {
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
        VisualizeCostPriceCommand command = new VisualizeCostPriceCommand(visualizer);

        assertThrows(PillException.class, () -> {
            command.execute(itemMap, storage);
        });
    }

    @Test
    public void execute_itemsWithNoCostOrPrice_throwsException() {
        itemMap.addItemSilent(new Item("Paracetamol", 10));
        VisualizeCostPriceCommand command = new VisualizeCostPriceCommand(visualizer);

        assertThrows(PillException.class, () -> {
            command.execute(itemMap, storage);
        });
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        VisualizeCostPriceCommand command = new VisualizeCostPriceCommand(visualizer);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
