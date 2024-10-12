package seedu.pill.command;

import seedu.pill.util.ItemList;

public class ListCommand extends Command{
    public ListCommand() {}

    @Override
    public void execute(ItemList itemList) {
        itemList.listItems();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
