package seedu.pill.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles timestamped input/output operations for the Pill inventory management system.
 * Provides methods to log both user inputs and system outputs with timestamps.
 */
public class TimestampIO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Prints a timestamped output message.
     * @param message - The message to be printed
     */
    public static void printOutput(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] OUT: " + message);
    }

    /**
     * Logs a timestamped input message.
     * @param input - The user input to be logged
     */
    public static void logInput(String input) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] IN: " + input);  // Changed "IN:  " to "IN: "
    }

    /**
     * Prints a timestamped error message.
     * @param error - The error message to be printed
     */
    public static void printError(String error) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] ERR: " + error);
    }
}
