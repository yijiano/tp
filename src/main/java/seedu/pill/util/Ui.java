package seedu.pill.util;

import java.util.Scanner;

public final class Ui {
    private final Scanner sc = new Scanner(System.in);
    private final ItemList items;

    public Ui(ItemList items) {
        this.items = items;
    }

    /**
     * Scans for user input.
     * @return The user input in string representation.
     */
    public String getInput() {
        Printer.printSpace();
        return this.sc.nextLine();
    }
}
