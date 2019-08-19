package search;

import java.util.HashMap;
import java.util.Map;

class SearchUtils {

    /**
     * Count all the appearances of a word in a given text and also count the appearances of similar words.
     *
     * @param entryText the text that will be searched in
     * @param keyword the word that will be searched
     * @param maxLevenshteinDistance the maximum Levenshtein distance allowed
     * @return a map of that counted the appearances of the keyword and also similar words
     */
    static Map<String, Integer> countWordAppearancesAndSimilar(String entryText, String keyword, int maxLevenshteinDistance) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        // handle special case where both strings are empty
        if (entryText.length() == 0 && keyword.length() == 0) {
            return wordCountMap;
        }

        for (String word : entryText.split("\\s+|\\W")) {
            if (wordCountMap.containsKey(word)) {
                wordCountMap.put(word, wordCountMap.get(word) + 1);
            } else if (word.equals(keyword)) {
                wordCountMap.put(keyword, 1);
            } else {
                switch (wordSimilarity(keyword, word, maxLevenshteinDistance)) {
                    case 0:
                    case 1:
                        wordCountMap.put(word, 1);
                        break;
                    default:
                }
            }
        }

        return wordCountMap;
    }

    /**
     * Check if two words are similar, meaning that their Levenshtein distance is greater than zero and smaller or equal
     * to the provided value in maxLevenshteinDistance.
     *
     * @param word1 first word
     * @param word2 second word
     * @param maxLevenshteinDistance the maximum Levenshtein distance allowed
     * @return it returns -1|0|1 where:
     * -1 means that the words are not similar (considering maxLevenshteinDistance)
     * 0 the words are equal
     * 1 the words are similar (considering maxLevenshteinDistance)
     */
    private static int wordSimilarity(String word1, String word2, int maxLevenshteinDistance) {
        // calculate difference between the words length
        int wordLenDiff = Math.abs(word1.length() - word2.length());
        // words can never be similar if the difference in string length is greater than maxLevenshteinDistance
        if (wordLenDiff > maxLevenshteinDistance) {
            return -1;
        }
        // words count as similar if maxLevenshteinDistance is equal or greater than both words length
        if (maxLevenshteinDistance >= word1.length() && maxLevenshteinDistance >= word2.length()) {
            return 1;
        }
        // calculate Levenshtein distance using the Wagner–Fischer algorithm
        int[][] distances = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    distances[i][j] = j;
                } else if (j == 0) {
                    distances[i][j] = i;
                } else {
                    distances[i][j] = Math.min(
                            Math.min(distances[i - 1][j] + 1, distances[i][j - 1] + 1),
                            distances[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }
        int levenshteinDistance = distances[word1.length()][word2.length()];

        if (levenshteinDistance > maxLevenshteinDistance) {
            return -1;
        } else if (levenshteinDistance == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
