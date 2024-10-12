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

    public static File initializeFile() throws IOException {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File items = new File(dir, FILE_NAME);
        items.createNewFile();
        return items;
    }

    public void saveItemList(ItemList items) throws PillException {
        try {
            File file = initializeFile();
            FileWriter fw = new FileWriter(file);
            for (Item item : items) {
                fw.write((item.getName() + SEPARATOR + item.getQuantity()) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new PillException(ExceptionMessages.SAVE_ERROR);
        }
    }

    public void saveItem(Item item) throws PillException {
        try {
            File file = initializeFile();
            FileWriter fw = new FileWriter(file, true);
            fw.write((item.getName() + SEPARATOR + item.getQuantity()) + System.lineSeparator());
        } catch (IOException e) {
            throw new PillException(ExceptionMessages.SAVE_ERROR);
        }
    }
}
