package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class ListCommand extends Command{
    public ListCommand() {}

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        itemMap.listItems();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
