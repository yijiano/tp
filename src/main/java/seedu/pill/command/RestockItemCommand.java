package seedu.pill.command;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Command to restock a specific item.
 */
public class RestockItemCommand extends Command {
    private final String itemName;
    private final Optional<LocalDate> expiryDate;
    private final int quantity;

    public RestockItemCommand(String itemName, Optional<LocalDate> expiryDate, int quantity) {
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        Item itemToRestock = itemMap.getItemByNameAndExpiry(itemName, expiryDate);

        if (itemToRestock == null) {
            throw new PillException(ExceptionMessages.ITEM_NOT_FOUND_ERROR);
        }

        int currentStock = itemToRestock.getQuantity();

        if (currentStock >= quantity) {
            System.out.printf("Item %s already has sufficient stock: %d (No restock needed)%n",
                    itemName, currentStock);
            return;
        }

        int restockAmount = quantity - currentStock;
        double restockCost = itemToRestock.getCost() * restockAmount;

        itemToRestock.setQuantity(quantity);

        System.out.printf("Restocked Item: %s, Current Stock: %d, New Stock: %d, Total Restock Cost: $%.2f%n",
                itemName, currentStock, itemToRestock.getQuantity(), restockCost);

        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
