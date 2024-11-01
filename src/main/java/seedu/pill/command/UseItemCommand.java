package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class UseItemCommand extends Command {
    private final String itemName;
    private final int quantityToUse;

    public UseItemCommand(String itemName, int quantityToUse) {
        this.itemName = itemName;
        this.quantityToUse = quantityToUse;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) throws PillException {
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
