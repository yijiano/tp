package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class StockCheckCommand extends Command{
    private final int threshold;

    public StockCheckCommand(String threshold) {
        this.threshold = Integer.parseInt(threshold);
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        itemMap.listItemsToRestock(this.threshold);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
