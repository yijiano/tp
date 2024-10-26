package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

public class ExpiredCommand extends Command {
    public ExpiredCommand() {}

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        ItemMap expiredItems = itemMap.getExpiredItems();
        ListCommand listCommand = new ListCommand();
        listCommand.execute(expiredItems, storage);
    }

    @Override
    public boolean isExit() { return false; }
}
