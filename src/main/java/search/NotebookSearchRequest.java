package search;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An object representation of a json request for searching a word inside a text.
 */
public class NotebookSearchRequest {

    @JsonProperty("entry_text")
    private final String entryText;
    @JsonProperty
    private final String keyword;

    public NotebookSearchRequest(String entryText, String keyword) {
        this.entryText = entryText;
        this.keyword = keyword;
    }

    String getEntryText() {
        return entryText;
    }

    String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return String.format("{\n\t'entry_text':'%s'\n\t'keyword':'%s'}", getEntryText(), getKeyword());
    }
}
