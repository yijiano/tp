package seedu.pill.command;

import seedu.pill.util.ItemList;

public class AddItemCommand extends Command {
    private final String itemName;
    private final int quantity;

    public AddItemCommand(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public void execute(ItemList itemList) {
        itemList.addItem(itemName, quantity);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
