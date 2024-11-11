package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Parser;
import seedu.pill.util.TransactionManager;
import seedu.pill.util.Ui;
import seedu.pill.util.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    private ItemMap itemMap;
    private Storage storage;
    private TransactionManager transactionManager;
    private Ui ui;
    private Parser parser;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        transactionManager = new TransactionManager(itemMap, storage);
        ui = new Ui(itemMap);
        parser = new Parser(itemMap, storage, transactionManager, ui);
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    // Basic Command Tests
    @Test
    public void parseCommand_exitCommand_setsExitFlag() {
        parser.parseCommand("exit");
        assertTrue(parser.getExitFlag());
    }

    @Test
    public void parseCommand_helpCommand_executesSuccessfully() {
        parser.parseCommand("help");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Available commands"));
    }

    @Test
    public void parseCommand_listCommand_executesSuccessfully() {
        parser.parseCommand("list");
        // Verify expected output
    }

    // Delete Command Tests
    @Test
    public void parseCommand_validDeleteCommand_deletesItem() throws PillException {
        itemMap.addItemSilent(new Item("Paracetamol", 10, LocalDate.parse("2024-12-31")));
        parser.parseCommand("delete Paracetamol 2024-12-31");
        assertTrue(itemMap.getItemByNameAndExpiry("Paracetamol",
                Optional.of(LocalDate.parse("2024-12-31"))) == null);
    }

    // Transaction Command Tests
    @Test
    public void parseCommand_listTransactions_executesSuccessfully() {
        parser.parseCommand("transactions");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("No transactions found"));
    }

    @Test
    public void parseCommand_validTransactionHistory_showsHistory() {
        parser.parseCommand("transaction-history 2024-01-01T00:00:00 2024-12-31T23:59:59");
        // Verify output
    }

    // Visualization Command Tests
    @Test
    public void parseCommand_visualizationCommands_executeSuccessfully() {
        parser.parseCommand("visualize-price");
        parser.parseCommand("visualize-cost");
        parser.parseCommand("visualize-stock");
        parser.parseCommand("visualize-cost-price");
    }

    // Error Cases
    @Test
    public void parseCommand_invalidCommand_throwsPillException() {
        parser.parseCommand("invalidcommand");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Invalid command, please try again."));
    }

    @Test
    public void parseCommand_emptyCommand_throwsPillException() {
        parser.parseCommand("");
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Invalid command, please try again."));
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
