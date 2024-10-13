package seedu.pill.command;

import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;


public class FindCommand extends Command {
    private final String itemName;

    public FindCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute(ItemList itemList, Storage storage) {
        ItemList foundItems = itemList.findItem(itemName);
        ListCommand listCommand = new ListCommand();
        listCommand.execute(foundItems, storage);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
