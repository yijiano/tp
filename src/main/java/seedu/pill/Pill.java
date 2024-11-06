package seedu.pill;

import seedu.pill.util.*;

import seedu.pill.exceptions.PillException;

import java.util.logging.Logger;

public final class Pill {
    private static final Storage storage = new Storage();
    private static ItemMap items = new ItemMap();
    private static final Ui ui = new Ui(items);
    private static final Logger logger = PillLogger.getLogger();
    private static TransactionManager transactionManager;
    private static Parser parser = new Parser(items, storage, transactionManager, ui);

    /**
     * Runs the main loop of the Pill chatbot.
     */
    public void run() {
        items = storage.loadData();
        transactionManager  = new TransactionManager(items);
        Printer.printInitMessage(items, 50);
        parser = new Parser(items, storage, transactionManager, ui);
        logger.info("New Chatbot Conversation Created");
        while (!parser.getExitFlag()) {
            String line = ui.getInput();
            parser.parseCommand(line);
        }
        Printer.printExitMessage();
        logger.info("Chatbot Conversation Ended");
    }

    /**
     * Main method to run the Pill bot.
     */
    public static void main(String[] args) throws PillException{
        new Pill().run();
    }
}
