package seedu.pill.util;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Storage class handles the storage of ItemList objects
 * in a file-based system, allowing for saving items and lists
 * of items to a specified text file.
 */
public class Storage {
    private static final String PATH = "./data/";
    private static final String FILE_NAME = "pill.txt";
    private static final String SEPARATOR = ",";

    /**
     * Initializes the storage file and creates the necessary
     * directories if they do not exist.
     *
     * @return The File object representing the storage file.
     * @throws IOException if an I/O error occurs during file creation.
     */
    private static File initializeFile() throws IOException {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File items = new File(dir, FILE_NAME);
        items.createNewFile();
        return items;
    }

    /**
     * Saves the provided ItemList to the storage file, overwriting
     * existing content.
     *
     * @param items The {@link ItemList} containing items to be saved.
     * @throws PillException if an error occurs during the saving process.
     */
    public void saveItemList(ItemList items) throws PillException {
        try {
            File file = initializeFile();
            FileWriter fw = new FileWriter(file);
            for (Item item : items) {
                fw.write((item.getName() + SEPARATOR + item.getQuantity()) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new PillException(ExceptionMessages.SAVE_ERROR);
        }
    }

    /**
     * Appends a single item to the storage file.
     *
     * @param item The {@link Item} to be saved.
     * @throws PillException if an error occurs during the saving process.
     */
    public void saveItem(Item item) throws PillException {
        try {
            File file = initializeFile();
            FileWriter fw = new FileWriter(file, true);
            fw.write((item.getName() + SEPARATOR + item.getQuantity()) + System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            throw new PillException(ExceptionMessages.SAVE_ERROR);
        }
    }
}
