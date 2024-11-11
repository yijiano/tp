package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Transaction;
import seedu.pill.util.TransactionManager;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for TransactionsCommand
 */
public class TransactionsCommandTest {
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
    public void execute_emptyTransactions_printsEmptyMessage() throws PillException {
        TransactionsCommand command = new TransactionsCommand(transactionManager);
        command.execute(itemMap, storage);
        assertTrue(outputStream.toString().trim().contains("No transactions found"));
    }

    @Test
    public void execute_withTransactions_listsAllTransactions() throws PillException {
        // Create a transaction by adding an incoming transaction
        transactionManager.createTransaction("Paracetamol", 10, null,
                Transaction.TransactionType.INCOMING, "Test transaction", null);

        TransactionsCommand command = new TransactionsCommand(transactionManager);
        command.execute(itemMap, storage);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Paracetamol"));
        assertTrue(output.contains("10"));
    }

    @Test
    public void isExit_returnsAlwaysFalse() {
        TransactionsCommand command = new TransactionsCommand(transactionManager);
        assertFalse(command.isExit());
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(standardOut);
    }
}
