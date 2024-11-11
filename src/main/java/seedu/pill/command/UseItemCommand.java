package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

/**
 * A command that uses (consumes) a specified quantity of items from the inventory.
 * This command updates both the in-memory item map and persists changes to storage.
 */
public class UseItemCommand extends Command {
    private final String itemName;
    private final int quantityToUse;

    /**
     * Creates a new command to use (consume) items from inventory.
     *
     * @param itemName the name of the item to use
     * @param quantityToUse the quantity of the item to consume
     */
    public UseItemCommand(String itemName, int quantityToUse) {
        this.itemName = itemName.toLowerCase();
        this.quantityToUse = quantityToUse;
    }

    /**
     * Executes the use item command by consuming the specified quantity of items
     * and saving the updated inventory to storage.
     *
     * @param itemMap the inventory to update
     * @param storage the storage system to save changes to
     * @throws PillException if the operation fails due to insufficient stock or invalid item name
     */
    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
        itemMap.useItem(this.itemName, this.quantityToUse);
        storage.saveItemMap(itemMap);
    }

    /**
     * Indicates whether this command should terminate the program.
     *
     * @return false as this command does not exit the program
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
