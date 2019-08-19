package search;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class NotebookSearchController {

    @CrossOrigin(origins = "*")
    @RequestMapping(method = POST, path = "/notebook/search")
    public NotebookWordSearchResult searchWord(@RequestBody NotebookSearchRequest notebookSearchRequest) {
        if (notebookSearchRequest.getEntryText() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'entry_text' is required.");
        }
        if (notebookSearchRequest.getKeyword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'keyword' is required.");
        }
        if (notebookSearchRequest.getKeyword().length() <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'keyword' must have a length of 2 or greater.");
        }

        Map<String, Integer> wordCountMap =
                SearchUtils.countWordAppearancesAndSimilar(notebookSearchRequest.getEntryText(), notebookSearchRequest.getKeyword(), 1);
        int frequency = wordCountMap.getOrDefault(notebookSearchRequest.getKeyword(), 0);
        wordCountMap.remove(notebookSearchRequest.getKeyword());

        return new NotebookWordSearchResult(notebookSearchRequest.getKeyword(), frequency, wordCountMap.keySet().toArray(new String[0]));
    }
}
