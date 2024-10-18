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

    public AddItemCommand(String itemName, int quantity, LocalDate expiryDate) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        itemMap.addItem(itemName, quantity, expiryDate);
        storage.saveItem(new Item(itemName, quantity, expiryDate));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
