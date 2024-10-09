package seedu.pill.util;

import java.util.Scanner;

public final class Ui {
    private final Scanner sc = new Scanner(System.in);

    /**
     * Scans for user input.
     * @return The user input in string representation.
     */
    public String getInput() {
        Printer.printLine();
        return this.sc.nextLine();
    }
}
