package seedu.pill.command;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;


public class FindCommand extends Command {
    private final String itemName;

    public FindCommand(String itemName) {
        this.itemName = itemName.toLowerCase();
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        ItemMap foundItems = itemMap.findItem(itemName);
        if (foundItems.isEmpty()) {
            throw new PillException(ExceptionMessages.ITEM_NOT_FOUND_ERROR);
        } else {
            ListCommand listCommand = new ListCommand();
            listCommand.execute(foundItems, storage);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
