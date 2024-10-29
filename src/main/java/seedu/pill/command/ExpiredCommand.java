package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

public class ExpiredCommand extends Command {
    public ExpiredCommand() {}

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        itemMap.listExpiringItems(LocalDate.now());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
