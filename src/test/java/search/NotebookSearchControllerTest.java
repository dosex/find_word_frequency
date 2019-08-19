package search;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A test class containing a number of basic tests for the NotebookSearchController.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotebookSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void searchWordTest1() throws Exception {
        NotebookSearchRequest nsr = new NotebookSearchRequest("Word Words Wor word", "Word");

        mvc.perform(MockMvcRequestBuilders.post("/notebook/search").content(asJsonString(nsr))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"keyword\":\"Word\",\"frequency\":1,\"similar_words\":[\"Words\",\"Wor\",\"word\"]}"));
    }

    @Test
    public void searchWordTest2() throws Exception {
        NotebookSearchRequest nsr = new NotebookSearchRequest("Word Word Word word", "Word");

        mvc.perform(MockMvcRequestBuilders.post("/notebook/search").content(asJsonString(nsr))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"keyword\":\"Word\",\"frequency\":3,\"similar_words\":[\"word\"]}"));
    }

    @Test
    public void searchWordTest3() throws Exception {
        NotebookSearchRequest nsr = new NotebookSearchRequest("", "Word");

        mvc.perform(MockMvcRequestBuilders.post("/notebook/search").content(asJsonString(nsr))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"keyword\":\"Word\",\"frequency\":0,\"similar_words\":[]}"));
    }

    @Test
    public void searchWordTest4() throws Exception {
        NotebookSearchRequest nsr = new NotebookSearchRequest("Word Words Wor word", "");

        mvc.perform(MockMvcRequestBuilders.post("/notebook/search").content(asJsonString(nsr))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchWordTest5() throws Exception {
        NotebookSearchRequest nsr = new NotebookSearchRequest("", "");

        mvc.perform(MockMvcRequestBuilders.post("/notebook/search").content(asJsonString(nsr))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchWordTest6() throws Exception {
        NotebookSearchRequest nsr = new NotebookSearchRequest("Word Word Word word ord wod wor wrd wOrD", "word");

        mvc.perform(MockMvcRequestBuilders.post("/notebook/search").content(asJsonString(nsr))
                            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"keyword\":\"word\",\"frequency\":1,\"similar_words\":[\"ord\",\"Word\",\"wod\",\"wor\",\"wrd\"]}"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}