package seedu.pill.command;

import seedu.pill.util.ItemList;

public class EditItemCommand extends Command {
    private final String itemName;
    private final int newQuantity;

    public EditItemCommand(String itemName, int newQuantity) {
        this.itemName = itemName;
        this.newQuantity = newQuantity;
    }

    @Override
    public void execute(ItemList itemList) {
        itemList.editItem(itemName, newQuantity);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
