package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to restock all items below a specified stock level.
 */
public class RestockAllCommand extends Command {
    private final int threshold;

    public RestockAllCommand(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        List<Item> itemsToRestock = new ArrayList<>();
        double totalRestockCost = 0;

        for (Item item : itemMap.getAllItems()) {
            if (item.getQuantity() < threshold) {
                int currentStock = item.getQuantity();
                int restockAmount = threshold - currentStock;
                double itemRestockCost = item.getCost() * restockAmount;

                totalRestockCost += itemRestockCost;
                item.setQuantity(threshold);

                itemsToRestock.add(item);
                System.out.printf("Item: %s, Current Stock: %d, New Stock: %d, Restock Cost: $%.2f%n",
                        item.getName(), currentStock, threshold, itemRestockCost);
            }
        }

        System.out.printf("Total Restock Cost for all items below threshold %d: $%.2f%n", threshold, totalRestockCost);
        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
