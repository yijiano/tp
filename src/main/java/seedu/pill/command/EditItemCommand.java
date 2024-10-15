package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class EditItemCommand extends Command {
    private final String itemName;
    private final int newQuantity;

    public EditItemCommand(String itemName, int newQuantity) {
        this.itemName = itemName;
        this.newQuantity = newQuantity;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        itemMap.editItem(itemName, newQuantity);
        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
