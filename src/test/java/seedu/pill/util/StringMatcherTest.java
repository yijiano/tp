package seedu.pill.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringMatcherTest {

    @Test
    public void levenshteinDistance_identicalStrings_returnsZero() {
        assertEquals(0, StringMatcher.levenshteinDistance("hello", "hello"));
        assertEquals(0, StringMatcher.levenshteinDistance("", ""));
        assertEquals(0, StringMatcher.levenshteinDistance("12345", "12345"));
    }

    @Test
    public void levenshteinDistance_singleCharacterDifference_returnsOne() {
        // Substitution
        assertEquals(1, StringMatcher.levenshteinDistance("cat", "hat"));
        // Deletion
        assertEquals(1, StringMatcher.levenshteinDistance("cats", "cat"));
        // Insertion
        assertEquals(1, StringMatcher.levenshteinDistance("cat", "cats"));
    }

    @Test
    public void levenshteinDistance_multipleCharacterDifferences_returnsCorrectDistance() {
        assertEquals(1, StringMatcher.levenshteinDistance("hello", "hallo")); // One substitution
        assertEquals(3, StringMatcher.levenshteinDistance("kitten", "sitting")); // Multiple operations
        assertEquals(3, StringMatcher.levenshteinDistance("saturday", "sunday")); // Multiple operations
    }

    @Test
    public void levenshteinDistance_differentLengthStrings_returnsCorrectDistance() {
        assertEquals(6, StringMatcher.levenshteinDistance("book", "bookkeeper")); // 6 insertions
        assertEquals(4, StringMatcher.levenshteinDistance("", "test")); // 4 insertions
        assertEquals(5, StringMatcher.levenshteinDistance("hello", "")); // 5 deletions
    }

    @Test
    public void levenshteinDistance_caseSensitive_respectsCase() {
        assertEquals(4, StringMatcher.levenshteinDistance("TEST", "test")); // 4 case changes
        assertEquals(1, StringMatcher.levenshteinDistance("Hello", "hello")); // 1 case change
    }

    // Rest of the test cases remain the same...
    @Test
    public void findClosestMatch_exactMatch_returnsMatch() {
        List<String> validStrings = Arrays.asList("help", "add", "delete");
        assertEquals("help", StringMatcher.findClosestMatch("help", validStrings));
    }

    @Test
    public void findClosestMatch_closeMatch_returnsClosestString() {
        List<String> validStrings = Arrays.asList("help", "add", "delete");
        assertEquals("help", StringMatcher.findClosestMatch("halp", validStrings));
        assertEquals("help", StringMatcher.findClosestMatch("helpp", validStrings));
        assertEquals("add", StringMatcher.findClosestMatch("ad", validStrings));
    }

    @Test
    public void findClosestMatch_noCloseMatch_returnsNull() {
        List<String> validStrings = Arrays.asList("help", "add", "delete");
        assertNull(StringMatcher.findClosestMatch("xxxxxxxx", validStrings));
        assertNull(StringMatcher.findClosestMatch("completely-different", validStrings));
    }

    @Test
    public void findClosestMatch_emptyInput_returnsNull() {
        List<String> validStrings = Arrays.asList("help", "add", "delete");
        assertNull(StringMatcher.findClosestMatch("", validStrings));
    }

    @Test
    public void findClosestMatch_emptyValidStrings_returnsNull() {
        assertNull(StringMatcher.findClosestMatch("help", Collections.emptyList()));
    }

    @Test
    public void findClosestMatch_caseInsensitive_returnsCaseInsensitiveMatch() {
        List<String> validStrings = Arrays.asList("Help", "ADD", "delete");
        assertEquals("Help", StringMatcher.findClosestMatch("help", validStrings));
        assertEquals("Help", StringMatcher.findClosestMatch("HELP", validStrings));
        assertEquals("ADD", StringMatcher.findClosestMatch("add", validStrings));
    }

    @Test
    public void findClosestMatch_multipleCloseMatches_returnsFirst() {
        List<String> validStrings = Arrays.asList("help", "heap", "heal");
        // "help" and "heap" are both distance 1 from "hepp", should return "help" as it's first in list
        assertEquals("help", StringMatcher.findClosestMatch("hepp", validStrings));
    }

    @Test
    public void findClosestMatch_specialCharacters_handlesCorrectly() {
        List<String> validStrings = Arrays.asList("user-input", "user_input", "user.input");
        assertEquals("user-input", StringMatcher.findClosestMatch("userinput", validStrings));
        assertEquals("user-input", StringMatcher.findClosestMatch("user input", validStrings));
    }

    @Test
    public void levenshteinDistance_specialCases_handlesCorrectly() {
        // Test with spaces
        assertEquals(1, StringMatcher.levenshteinDistance("hello world", "hello  world"));
        // Test with numbers
        assertEquals(1, StringMatcher.levenshteinDistance("test123", "test124"));
        // Test with special characters
        assertEquals(1, StringMatcher.levenshteinDistance("user-input", "user_input"));
    }
}
