package seedu.pill.command;

import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;

public class ListCommand extends Command{
    public ListCommand() {}

    @Override
    public void execute(ItemList itemList, Storage storage) {
        itemList.listItems();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
