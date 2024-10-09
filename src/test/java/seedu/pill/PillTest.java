package seedu.pill;

import seedu.pill.exceptions.PillException;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class PillTest {
    @Test
    public void uiExitTest() throws PillException {
        // Prepare input
        String input = "exit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Run the Pill program
        Pill.main(new String[]{});

        // Get the output
        String output = outputStream.toString();

        // Assert that the exit message is printed
        assertTrue(output.contains("Bye. Hope to see you again soon!"));
    }
}
