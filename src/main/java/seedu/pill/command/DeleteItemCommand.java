package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;

public class DeleteItemCommand extends Command {
    private final String itemName;

    public DeleteItemCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute(ItemList itemList, Storage storage) throws PillException {
        itemList.deleteItem(itemName);
        storage.saveItemList(itemList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
