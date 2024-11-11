//@@author philip1304-unused
// Same reason given in TimestampIO.java
package seedu.pill.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimestampIOTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Verifies that a timestamped output string matches the expected format and content.
     */
    private boolean verifyTimestampedOutput(String output, String expectedMessageType, String expectedContent) {
        // Check basic format
        if (!output.startsWith("[") || output.length() < 21) {
            return false;
        }

        try {
            // Extract timestamp
            String timestamp = output.substring(1, 20);
            LocalDateTime outputTime = LocalDateTime.parse(timestamp, formatter);

            // Verify timestamp is recent
            LocalDateTime now = LocalDateTime.now();
            long timeDiff = ChronoUnit.SECONDS.between(outputTime, now);
            if (Math.abs(timeDiff) > 1) {
                return false;
            }

            // Verify message format
            String expectedFormat = String.format("[%s] %s: %s",
                    timestamp,
                    expectedMessageType,
                    expectedContent);

            return output.equals(expectedFormat);

        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void printOutput_simpleMessage_formatsCorrectly() {
        String message = "Test message";
        TimestampIO.printOutput(message);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "OUT", message));
    }

    @Test
    void printOutput_messageWithSpecialCharacters_formatsCorrectly() {
        String message = "Test message with !@#$%^&*()";
        TimestampIO.printOutput(message);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "OUT", message));
    }

    @Test
    void printError_simpleError_formatsCorrectly() {
        String error = "Test error";
        TimestampIO.printError(error);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "ERR", error));
    }

    @Test
    void printError_errorWithSpecialCharacters_formatsCorrectly() {
        String error = "Test error with !@#$%^&*()";
        TimestampIO.printError(error);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "ERR", error));
    }

    @Test
    void output_withNewlines_preservesFormatting() {
        String message = "Line 1\nLine 2\nLine 3";
        TimestampIO.printOutput(message);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "OUT", message));
    }
}
