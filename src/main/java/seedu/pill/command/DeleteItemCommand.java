package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

public class DeleteItemCommand extends Command {
    private final String itemName;
    private final LocalDate expiryDate;

    public DeleteItemCommand(String itemName) {
        this.itemName = itemName;
        this.expiryDate = null;
    }

    public DeleteItemCommand(String itemName, LocalDate expiryDate) {
        this.itemName = itemName;
        this.expiryDate = expiryDate;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        itemMap.deleteItem(itemName, expiryDate);
        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
