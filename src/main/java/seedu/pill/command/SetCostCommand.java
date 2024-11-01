package seedu.pill.command;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

/**
 * Command to set the cost of all items with a specified name.
 */
public class SetCostCommand extends Command {
    private final String itemName;
    private final double cost;

    public SetCostCommand(String itemName, double cost) {
        this.itemName = itemName;
        this.cost = cost;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        boolean itemFound = false;
        boolean msgIsPrinted = false;

        for (Item item : itemMap.getItemsByName(itemName)) {
            item.setCost(cost);
            if (!msgIsPrinted) {
                System.out.println("Set cost of " + itemName +  " to $" + cost + ".");
                msgIsPrinted = true;
            }
            itemFound = true;
        }

        if (!itemFound) {
            throw new PillException(ExceptionMessages.ITEM_NOT_FOUND_ERROR);
        }

        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
