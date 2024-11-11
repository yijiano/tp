package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.TransactionManager;

import java.time.LocalDate;

public class TransactionHistoryCommand extends Command {
    private TransactionManager transactionManager;
    private LocalDate start;
    private LocalDate end;

    public TransactionHistoryCommand(LocalDate start, LocalDate end, TransactionManager transactionManager) {
        this.start = start;
        this.end = end;
        this.transactionManager = transactionManager;
    }
    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        transactionManager.listTransactionHistory(start, end);
    }
}
