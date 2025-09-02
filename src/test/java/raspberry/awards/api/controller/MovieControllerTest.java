package raspberry.awards.api.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import raspberry.awards.api.service.MovieService;

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



}
