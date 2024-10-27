package seedu.pill.util;

import java.util.List;

/**
 * A utility class that provides string matching and comparison functionality.
 * This class implements methods to find similar strings and calculate string distances,
 * which is particularly useful for command suggestions and error handling.
 */
public class StringMatcher {

    /**
     * Calculates the Levenshtein distance between two strings.
     * The Levenshtein distance is the minimum number of single-character edits
     * (insertions, deletions, or substitutions) required to change one string into another.
     *
     * @param s1 - The first string to compare
     * @param s2 - The second string to compare
     * @return   - The minimum number of edits needed to transform s1 into s2
     */
    public static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    /**
     * Helper method to find the minimum of three integers.
     * Used by the levenshteinDistance method to determine the smallest edit distance.
     *
     * @param a - The first integer to compare
     * @param b - The second integer to compare
     * @param c - The third integer to compare
     * @return  - The smallest value among the three input integers
     */
    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Finds the closest matching string from a list of valid strings.
     * A string is considered a close match if its Levenshtein distance from the input
     * is 2 or less. If multiple strings have the same distance, returns the first match found.
     * The comparison is case-insensitive.
     *
     * @param input        - The input string to find matches for
     * @param validStrings - A list of valid strings to compare against
     * @return             - The closest matching string, or null if no match is found within distance of 2
     */
    public static String findClosestMatch(String input, List<String> validStrings) {
        String closestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (String valid : validStrings) {
            int distance = levenshteinDistance(input.toLowerCase(), valid.toLowerCase());
            if (distance < minDistance && distance <= 2) {  // Allow up to 2 edits
                minDistance = distance;
                closestMatch = valid;
            }
        }

        return closestMatch;
    }
}
