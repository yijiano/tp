package seedu.pill;

import seedu.pill.util.Ui;
import seedu.pill.util.Printer;

public final class Pill {
    private static final Ui ui = new Ui();

    /**
     * Runs the main loop of the Pill chatbot.
     */
    public void run(){
        Printer.printInitMessage();
        String line = ui.getInput();
        while (!line.equalsIgnoreCase("exit")) {
            System.out.println("No such command yet, please try again next year.");
            line = ui.getInput();
        }
        Printer.printExitMessage();
    }

    /**
     * Main method to run the Pill bot.
     */
    public static void main(String[] args){
        new Pill().run();
    }
}