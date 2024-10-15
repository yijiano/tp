package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class DeleteItemCommand extends Command {
    private final String itemName;

    public DeleteItemCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        itemMap.deleteItem(itemName);
        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
