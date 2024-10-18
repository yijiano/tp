package seedu.pill;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Parser;
import seedu.pill.util.Printer;
import seedu.pill.util.Storage;
import seedu.pill.util.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Pill {
    private static final Storage storage = new Storage();
    private static ItemMap items = new ItemMap();
    private static final Ui ui = new Ui(items);
    private static Parser parser = new Parser(items, storage);

    /**
     * Runs the main loop of the Pill chatbot.
     */
    public void run() {
        Logger LOGGER = Logger.getLogger("PILL");
        LOGGER.setLevel(Level.OFF);
        items = storage.loadData();
        Printer.printInitMessage();
        parser = new Parser(items, storage);
        LOGGER.info("New Chatbot Conversation Created");
        while (!parser.getExitFlag()) {
            String line = ui.getInput();
            parser.parseCommand(line);
        }
        Printer.printExitMessage();
        LOGGER.info("Chatbot Conversation Ended");
    }

    /**
     * Main method to run the Pill bot.
     */
    public static void main(String[] args) throws PillException{
        new Pill().run();
    }
}
