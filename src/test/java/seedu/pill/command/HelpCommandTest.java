package seedu.pill.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelpCommandTest {
    private ItemMap itemMap;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        itemMap = new ItemMap();
        storage = new Storage();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void execute_emptyCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("Type 'help <command>' for more information"));
    }

    @Test
    void execute_nullCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand(null, false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("Type 'help <command>' for more information"));
    }

    @Test
    void execute_helpForHelp_printsHelpHelp() throws PillException {
        HelpCommand command = new HelpCommand("help", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_verboseHelpForHelp_printsDetailedHelpHelp() throws PillException {
        HelpCommand command = new HelpCommand("help", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_verboseHelpForDelete_printsDetailedDeleteHelp() throws PillException {
        HelpCommand command = new HelpCommand("delete", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: delete <name> <expiry>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForEdit_printsDetailedEditHelp() throws PillException {
        HelpCommand command = new HelpCommand("edit", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: edit <name> <quantity> <expiry>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForList_printsDetailedListHelp() throws PillException {
        HelpCommand command = new HelpCommand("list", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: list"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForStockCheck_printsDetailedStockCheckHelp() throws PillException {
        HelpCommand command = new HelpCommand("stock-check", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: stock-check <threshold>"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_verboseHelpForExit_printsDetailedExitHelp() throws PillException {
        HelpCommand command = new HelpCommand("exit", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: exit"));
        assertTrue(output.contains("Example:"));
    }

    @Test
    void execute_invalidCommand_suggestsSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("hel", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: help?"));
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_veryInvalidCommand_showsNoSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("xyz", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
        assertTrue(output.contains("No similar command found"));
    }

    @Test
    void execute_nullItemMap_throwsAssertionError() {
        HelpCommand command = new HelpCommand("help", false);
        assertThrows(AssertionError.class, () -> command.execute(null, storage));
    }

    @Test
    void execute_nullStorage_throwsAssertionError() {
        HelpCommand command = new HelpCommand("help", false);
        assertThrows(AssertionError.class, () -> command.execute(itemMap, null));
    }

    @Test
    void isExit_returnsAlwaysFalse() {
        HelpCommand command = new HelpCommand("help", false);
        assertFalse(command.isExit());
    }

    @AfterEach
    void restoreSystemStreams() {
        System.setOut(originalOut);
    }

    @Test
    void execute_helpWithMultipleWords_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("help more words", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_multiWordInvalidCommand_showsNoSimilarCommand() throws PillException {
        HelpCommand command = new HelpCommand("invalid command string", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
        assertTrue(output.contains("No similar command found"));
    }

    @Test
    void execute_almostList_suggestsListCommand() throws PillException {
        HelpCommand command = new HelpCommand("lst", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: list?"));
    }

    @Test
    void execute_almostAdd_suggestsAddCommand() throws PillException {
        HelpCommand command = new HelpCommand("ad", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: add?"));
    }

    @Test
    void execute_almostDelete_suggestsDeleteCommand() throws PillException {
        HelpCommand command = new HelpCommand("delet", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: delete?"));
    }

    @Test
    void execute_almostEdit_suggestsEditCommand() throws PillException {
        HelpCommand command = new HelpCommand("edt", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: edit?"));
    }

    @Test
    void execute_almostStockCheck_suggestsStockCheckCommand() throws PillException {
        HelpCommand command = new HelpCommand("stock-chek", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: stock-check?"));
    }

    @Test
    void execute_almostExit_suggestsExitCommand() throws PillException {
        HelpCommand command = new HelpCommand("ext", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Did you mean: exit?"));
    }

    @Test
    void execute_commandWithExtraSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help    -v", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
    }

    @Test
    void execute_mixedCaseCommand_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("HeLp", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_commandWithTrailingSpaces_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help   ", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("help: Shows help information"));
    }

    @Test
    void execute_verboseWithExtraFlags_stillShowsVerbose() throws PillException {
        HelpCommand command = new HelpCommand("help -v -x", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
        assertTrue(output.contains("Examples:"));
    }

    @Test
    void execute_helpWithSpecialCharacters_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help#$%", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_emptyStringCommand_printsGeneralHelp() throws PillException {
        HelpCommand command = new HelpCommand("    ", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void execute_helpWithNumbers_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help123", false);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command:"));
    }

    @Test
    void execute_caseInsensitiveVerboseFlag_handlesCorrectly() throws PillException {
        HelpCommand command = new HelpCommand("help -V", true);
        command.execute(itemMap, storage);

        String output = outContent.toString();
        assertTrue(output.contains("Usage: help [command] [-v]"));
    }
}
