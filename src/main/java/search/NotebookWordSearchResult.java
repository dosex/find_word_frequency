package search;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An object representation of a json response for counting the appearances of a word inside a text.
 */
public class NotebookWordSearchResult {

    private final String keyword;
    private final int frequency;
    @JsonProperty("similar_words")
    private final String[] similarWords;

    NotebookWordSearchResult(String keyword, int frequency, String[] similarWords) {
        this.keyword = keyword;
        this.frequency = frequency;
        this.similarWords = similarWords;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getFrequency() {
        return frequency;
    }

    public String[] getSimilarWords() {
        return similarWords;
    }
}
