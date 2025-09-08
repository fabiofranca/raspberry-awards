package raspberry.awards.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AwardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoadCsvOnStartup() throws Exception {
        mockMvc.perform(get("/award/min-max-gap-btw-wins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray());
    }

    @Test
    void shouldReturnCorrectStructure() throws Exception {
        String response = mockMvc.perform(get("/award/min-max-gap-btw-wins"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode root = objectMapper.readTree(response);

        assertThat(root.has("min")).isTrue();
        assertThat(root.has("max")).isTrue();

        JsonNode firstMin = root.get("min").get(0);
        assertThat(firstMin.has("producerName")).isTrue();
        assertThat(firstMin.has("intervalBetweenWins")).isTrue();
        assertThat(firstMin.has("previousWin")).isTrue();
        assertThat(firstMin.has("followingWin")).isTrue();
    }
}
