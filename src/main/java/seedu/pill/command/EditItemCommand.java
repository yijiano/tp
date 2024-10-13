package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemList;
import seedu.pill.util.Storage;

public class EditItemCommand extends Command {
    private final String itemName;
    private final int newQuantity;

    public EditItemCommand(String itemName, int newQuantity) {
        this.itemName = itemName;
        this.newQuantity = newQuantity;
    }

    @Override
    public void execute(ItemList itemList, Storage storage) throws PillException {
        itemList.editItem(itemName, newQuantity);
        storage.saveItemList(itemList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
