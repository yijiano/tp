package seedu.pill.command;

import seedu.pill.util.ItemList;

public class DeleteItemCommand extends Command {
    private final String itemName;

    public DeleteItemCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public void execute(ItemList itemList) {
        itemList.deleteItem(itemName);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
