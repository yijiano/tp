package seedu.pill.command;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.text.DecimalFormat;

/**
 * Command to set the price of all items with a specified name.
 */
public class SetPriceCommand extends Command {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private final String itemName;
    private final double price;

    public SetPriceCommand(String itemName, double price) {
        this.itemName = itemName.toLowerCase();
        this.price = price;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        boolean itemFound = false;
        boolean msgIsPrinted = false;

        for (Item item : itemMap.getItemsByName(itemName)) {
            item.setPrice(price);
            if (!msgIsPrinted) {
                System.out.println("Set price of " + itemName + " to $" + decimalFormat.format(price) + ".");
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
