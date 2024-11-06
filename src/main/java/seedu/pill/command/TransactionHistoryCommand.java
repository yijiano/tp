package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.TransactionManager;

import java.time.LocalDateTime;

public class TransactionHistoryCommand extends Command {
    private TransactionManager transactionManager;
    private LocalDateTime start;
    private LocalDateTime end;

    public TransactionHistoryCommand(LocalDateTime start, LocalDateTime end, TransactionManager transactionManager) {
        this.start = start;
        this.end = end;
        this.transactionManager = transactionManager;
    }
    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        transactionManager.listTransactionHistory(start, end);
    }
}
