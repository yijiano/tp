package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Order;
import seedu.pill.util.Storage;
import seedu.pill.util.TransactionManager;

public class FulfillCommand extends Command {
    private Order order;
    private TransactionManager transactionManager;

    public FulfillCommand(Order order, TransactionManager transactionManager) {
        this.order = order;
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        transactionManager.fulfillOrder(order);
    }
}
