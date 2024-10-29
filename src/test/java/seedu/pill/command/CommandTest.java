package seedu.pill.command;

import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CommandTest {

    @Test
    void isExit_returnsAlwaysFalse() {
        Command command = new Command() {
            @Override
            public void execute(ItemMap itemMap, Storage storage) throws PillException {
                // Test implementation
            }
        };
        assertFalse(command.isExit());
    }
}
