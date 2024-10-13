package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;

public class AddItemCommand extends Command {
    private final String itemName;
    private final int quantity;

    public AddItemCommand(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public void execute(ItemList itemList, Storage storage) throws PillException {
        itemList.addItem(itemName, quantity);
        storage.saveItem(new Item(itemName, quantity));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
