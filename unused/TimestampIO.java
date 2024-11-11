//@@author philip1304-unused
/*
 * We did not get around to integrating timestamps since we had too many bugs
 * in our code and many other features that needed integrating at the same time.
 */
package seedu.pill.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles timestamped input/output operations for the Pill inventory management system.
 * Provides methods to log both user inputs and system outputs with timestamps.
 * Supports full Unicode character set for internationalization.
 */
public class TimestampIO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Prints a timestamped output message.
     * @param message - The message to be printed
     */
    public static void printOutput(String message) {
        printTimestamped("OUT", message);
    }

    /**
     * Logs a timestamped input message.
     * @param input - The user input to be logged
     */
    public static void logInput(String input) {
        printTimestamped("IN", input);
    }

    /**
     * Prints a timestamped error message.
     * @param error - The error message to be printed
     */
    public static void printError(String error) {
        printTimestamped("ERR", error);
    }

    /**
     * Helper method to print timestamped messages in a consistent format.
     * @param type - The type of message (IN/OUT/ERR)
     * @param message - The message content
     */
    private static void printTimestamped(String type, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.printf("[%s] %s: %s%n", timestamp, type, message);
    }
}
