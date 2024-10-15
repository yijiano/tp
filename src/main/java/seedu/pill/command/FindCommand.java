package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;


public class FindCommand extends Command {
    private final String itemName;

    public FindCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        ItemMap foundItems = itemMap.findItem(itemName);
        ListCommand listCommand = new ListCommand();
        listCommand.execute(foundItems, storage);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
