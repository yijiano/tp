package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class ExpiredCommand extends Command {
    public ExpiredCommand() {}

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        itemMap.listExpiredItems();
    }

    @Override
    public boolean isExit() { return false; }
}
