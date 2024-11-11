package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.TransactionManager;
import seedu.pill.util.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for TransactionHistoryCommand
 */
public class TransactionHistoryCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private TransactionManager transactionManager;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        transactionManager = new TransactionManager(itemMap, storage);
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @Test
    public void execute_validDateRange_showsTransactions() throws PillException {
        // Create a transaction
        transactionManager.createTransaction("Paracetamol", 10, null,
                Transaction.TransactionType.INCOMING, "Past transaction", null);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().plusDays(1);

        TransactionHistoryCommand command = new TransactionHistoryCommand(start, end, transactionManager);
        command.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Paracetamol"));
        assertTrue(output.contains("10"));
    }

    @Test
    public void execute_emptyDateRange_printsNothing() throws PillException {
        LocalDate now = LocalDate.now();
        TransactionHistoryCommand command = new TransactionHistoryCommand(now, now, transactionManager);

        command.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.isEmpty());
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        LocalDate now = LocalDate.now();
        TransactionHistoryCommand command = new TransactionHistoryCommand(now, now, transactionManager);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
