package seedu.pill.util;

import org.junit.jupiter.api.Test;
import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LoadDataTest {
    @Test
    public void loadLineSimplePasses() throws PillException {
        String data = "Bandages,20";
        Item expectedItem = new Item("Bandages", 20);
        Storage storage = new Storage();
        Item item = storage.loadLine(data);
        assertEquals(expectedItem, item);
    }

    @Test
    public void loadLineCorruptThrowsException() {
        String data = "Bandages,20,5";
        Storage storage = new Storage();
        try {
            Item item = storage.loadLine(data);
            fail();
        } catch (PillException e) {
            assertEquals(ExceptionMessages.PARSE_DATE_ERROR.getMessage(), e.getMessage());
        }
    }

    @Test
    public void loadLineInvalidQuantityThrowsException() {
        String data = "Bandages,20a";
        Storage storage = new Storage();
        try {
            Item item = storage.loadLine(data);
            fail();
        } catch (PillException e) {
            assertEquals(ExceptionMessages.INVALID_QUANTITY_FORMAT.getMessage(), e.getMessage());
        }
    }
}
