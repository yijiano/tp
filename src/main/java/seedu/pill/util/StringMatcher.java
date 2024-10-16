package seedu.pill.util;

import java.util.List;

public class StringMatcher {

    /**
     * Calculates the Levenshtein distance between two strings.
     * @param s1 The first string
     * @param s2 The second string
     * @return The Levenshtein distance
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

    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Finds the closest match to the input string from a list of valid strings.
     * @param input The input string
     * @param validStrings List of valid strings
     * @return The closest match, or null if no close match found
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
