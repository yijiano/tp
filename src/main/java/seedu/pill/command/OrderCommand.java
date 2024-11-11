package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.TransactionManager;
import seedu.pill.util.Order;
import seedu.pill.util.Storage;


public class OrderCommand extends Command{
    private TransactionManager transactionManager;
    private ItemMap itemsToOrder;
    private Order.OrderType orderType;
    private String notes;

    public OrderCommand(ItemMap itemsToOrder, TransactionManager transactionManager,
                        Order.OrderType orderType, String notes) {
        this.itemsToOrder = itemsToOrder;
        this.transactionManager = transactionManager;
        this.orderType = orderType;
        this.notes = notes;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        transactionManager.createOrder(orderType, itemsToOrder, notes);
    }
}
