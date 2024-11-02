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
    private static final PrintStream originalOut = System.out;
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
        // Debug print to see actual output
        System.setOut(originalOut);
        System.out.println("Actual output: '" + output + "'");
        String expectedPattern = String.format("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\] %s: %s",
                                 expectedMessageType, java.util.regex.Pattern.quote(expectedContent));
        System.out.println("Expected pattern: " + expectedPattern);
        System.out.println("Pattern matches: " + output.matches(expectedPattern));
        System.setOut(new PrintStream(outContent));

        try {
            // Check if output matches basic format
            if (!output.startsWith("[") || output.length() < 21) {
                System.setOut(originalOut);
                System.out.println("Format check failed: Output doesn't start with [ or is too short");
                System.setOut(new PrintStream(outContent));
                return false;
            }

            // Extract timestamp
            String timestamp = output.substring(1, 20);
            LocalDateTime outputTime;
            try {
                outputTime = LocalDateTime.parse(timestamp, formatter);
            } catch (Exception e) {
                System.setOut(originalOut);
                System.out.println("Timestamp parse failed: " + timestamp);
                System.setOut(new PrintStream(outContent));
                return false;
            }

            // Verify timestamp is recent
            LocalDateTime now = LocalDateTime.now();
            long timeDiff = ChronoUnit.SECONDS.between(outputTime, now);
            if (Math.abs(timeDiff) > 1) {
                System.setOut(originalOut);
                System.out.println("Time difference too large: " + timeDiff + " seconds");
                System.setOut(new PrintStream(outContent));
                return false;
            }

            boolean matches = output.matches(expectedPattern);
            if (!matches) {
                System.setOut(originalOut);
                System.out.println("Pattern match failed. Expected pattern: " + expectedPattern);
                System.setOut(new PrintStream(outContent));
            }
            return matches;

        } catch (Exception e) {
            System.setOut(originalOut);
            System.out.println("Verification failed with exception: " + e.getMessage());
            System.setOut(new PrintStream(outContent));
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

    @Test
    void output_withUnicode_handlesCorrectly() {
        String message = "Test with Unicode: こんにちは 你好 안녕하세요";
        TimestampIO.printOutput(message);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "OUT", message));
    }

    @Test
    void logInput_simpleInput_formatsCorrectly() {
        String input = "test input";
        TimestampIO.logInput(input);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "IN", input));
    }

    @Test
    void logInput_inputWithSpecialCharacters_formatsCorrectly() {
        String input = "test input with !@#$%^&*()";
        TimestampIO.logInput(input);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "IN", input));
    }

    @Test
    void logInput_multilineInput_formatsCorrectly() {
        String input = "line 1\nline 2\nline 3";
        TimestampIO.logInput(input);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "IN", input));
    }

    @Test
    void logInput_unicodeInput_handlesCorrectly() {
        String input = "Input with Unicode: こんにちは 你好 안녕하세요";
        TimestampIO.logInput(input);
        String output = outContent.toString().trim();
        assertTrue(verifyTimestampedOutput(output, "IN", input));
    }
}
