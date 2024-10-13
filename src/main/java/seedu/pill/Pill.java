package seedu.pill;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemList;
import seedu.pill.util.Parser;
import seedu.pill.util.Printer;
import seedu.pill.util.Storage;
import seedu.pill.util.Ui;

public final class Pill {
    private static final ItemList items = new ItemList();
    private static final Storage storage = new Storage();
    private static final Ui ui = new Ui(items);
    private static final Parser parser = new Parser(items, storage);

    /**
     * Runs the main loop of the Pill chatbot.
     */
    public void run() throws PillException {
        Printer.printInitMessage();
        String line = ui.getInput();
        while (!parser.getExitFlag()) {
            parser.parseCommand(line);
            line = ui.getInput();
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
