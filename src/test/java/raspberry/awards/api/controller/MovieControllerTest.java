package raspberry.awards.api.controller;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raspberry.awards.api.InvalidCSVFormatException;
import raspberry.awards.api.service.MovieService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    private  static final String MOVIELIST_CSV = "movielist.csv";
    private  static final String INVALID_CSV = "invalid.csv";

    @Autowired
    private MovieController movieController;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Test
    public void whenUploadCSV_ReturnCreated() throws Exception{

        byte[] bytes = IOUtils.resourceToByteArray(MOVIELIST_CSV,
                getClass().getClassLoader());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/csv")
                .file("csvFile", bytes))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenUploadInvalidCSV_ReturnError() throws Exception {
        byte[] bytes = IOUtils.resourceToByteArray(INVALID_CSV,
                getClass().getClassLoader());
        when(movieService.truncateAndSaveNewData(bytes))
                .thenThrow(new InvalidCSVFormatException(new IllegalStateException()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/csv")
                        .file("csvFile", bytes))
                .andExpect(status().isBadRequest());

    }


}
