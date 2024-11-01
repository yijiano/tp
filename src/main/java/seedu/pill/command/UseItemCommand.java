package seedu.pill.command;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class UseItemCommand extends Command {
    private final String itemName;
    private final int quantityToUse;

    public UseItemCommand(String itemName, int quantityToUse) {
        this.itemName = itemName;
        this.quantityToUse = quantityToUse;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        int quantityLeftToUse = this.quantityToUse;

        if (quantityLeftToUse > itemMap.stockCount(this.itemName)) {
            throw new PillException(ExceptionMessages.STOCK_UNDERFLOW);
        }

        while (quantityLeftToUse > 0) {
            // throws PillException if no key-value pair for itemName exists
            Item itemToUse = itemMap.getSoonestExpiringItem(this.itemName);

            if (itemToUse.getQuantity() == quantityLeftToUse) {
                quantityLeftToUse = 0;
                itemMap.deleteItem(this.itemName, itemToUse.getExpiryDate());
            } else if (itemToUse.getQuantity() > quantityLeftToUse) {
                quantityLeftToUse = 0;
                itemToUse.setQuantity(itemToUse.getQuantity() - quantityLeftToUse);
                itemMap.editItem(itemToUse);
            } else {
                quantityLeftToUse -= itemToUse.getQuantity();
                itemMap.deleteItem(this.itemName, itemToUse.getExpiryDate());
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
