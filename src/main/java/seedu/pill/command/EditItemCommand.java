package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

public class EditItemCommand extends Command {
    private final String itemName;
    private final int newQuantity;
    private final LocalDate expiryDate;

    public EditItemCommand(String itemName, int newQuantity) {
        this.itemName = itemName.toLowerCase();
        this.newQuantity = newQuantity;
        this.expiryDate = null;
    }

    public EditItemCommand(String itemName, int newQuantity, LocalDate expiryDate) {
        this.itemName = itemName.toLowerCase();
        this.newQuantity = newQuantity;
        this.expiryDate = expiryDate;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        Item item = new Item(itemName, newQuantity, expiryDate);
        itemMap.editItem(item);
        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
