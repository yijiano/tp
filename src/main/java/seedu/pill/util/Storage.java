package seedu.pill.util;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private static final String PATH = "./data/";
    private static final String FILE_NAME = "pill.txt";
    private static final String SEPARATOR = ",";

    /**
     * Saves the current state of the item list to the storage file. Each item
     * is saved in Comma Separated Values(CSV) format.
     *
     * @param items The {@link ItemList} to be saved.
     */
    public void save(ItemList items) throws PillException {
        File file = new File(PATH + FILE_NAME);

        try (FileWriter fw = new FileWriter(file)) {
            for (Item item : items) {
                fw.write((item.getName() + SEPARATOR + item.getQuantity()) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new PillException(ExceptionMessages.SAVE_ERROR);
        }
    }
}
