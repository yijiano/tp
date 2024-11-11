package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.Item;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

public class AddItemCommand extends Command {
    private final String itemName;
    private final int quantity;
    private final LocalDate expiryDate;

    public AddItemCommand(String itemName, int quantity) {
        this.itemName = itemName.toLowerCase();
        this.quantity = quantity;
        this.expiryDate = null;
    }

    public AddItemCommand(String itemName, int quantity, LocalDate expiryDate) {
        this.itemName = itemName.toLowerCase();
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        Item item = new Item(itemName, quantity, expiryDate);
        itemMap.addItem(item);
        storage.saveItem(item);
        storage.saveItemMap(itemMap);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
