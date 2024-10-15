package seedu.pill;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Parser;
import seedu.pill.util.Printer;
import seedu.pill.util.Storage;
import seedu.pill.util.Ui;

public final class Pill {
    private static final Storage storage = new Storage();
    private static ItemMap items = new ItemMap();
    private static final Ui ui = new Ui(items);
    private static Parser parser = new Parser(items, storage);

    /**
     * Runs the main loop of the Pill chatbot.
     */
    public void run() throws PillException {
        Printer.printInitMessage();
        items = storage.loadData();
        parser = new Parser(items, storage);
        while (!parser.getExitFlag()) {
            String line = ui.getInput();
            parser.parseCommand(line);
        }
        Printer.printExitMessage();
    }

    /**
     * Main method to run the Pill bot.
     */
    public static void main(String[] args) throws PillException{
        new Pill().run();
    }
}
