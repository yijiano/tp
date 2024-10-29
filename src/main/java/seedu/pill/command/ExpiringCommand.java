package seedu.pill.command;

import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.time.LocalDate;

public class ExpiringCommand extends Command {
    private LocalDate cutOffDate;

    public ExpiringCommand(LocalDate cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

    @Override
    public void execute(ItemMap itemMap, Storage storage) {
        itemMap.listExpiringItems(cutOffDate);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
