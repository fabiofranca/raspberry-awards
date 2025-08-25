package raspberry.awards.api.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import raspberry.awards.api.InvalidCSVFormatException;
import raspberry.awards.api.persistency.MovieRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MovieServiceTest {

    private  static final String MOVIELIST_CSV = "movielist.csv";
    private  static final String MOVIELIST_WITHOUT_HEADER_CSV = "movielist_without_header.csv";

    @Autowired
    private MovieService movieService;

    @MockitoBean
    private MovieRepository movieRepository;

    @Test
    public void whenReceiveValidCSV_NotThrowException() throws Exception{
        byte[] bytes = IOUtils.resourceToByteArray(MOVIELIST_CSV,
                getClass().getClassLoader());

        assertDoesNotThrow(() ->{
            movieService.truncateAndSaveNewData(bytes);
        });
    }

    @Test
    public void whenReceiveCsvWithoutHeader_ThrowException() throws IOException {
        byte[] bytes = IOUtils.resourceToByteArray(MOVIELIST_WITHOUT_HEADER_CSV,
                getClass().getClassLoader());
        InvalidCSVFormatException invalidCSVFormatException =
                assertThrows(InvalidCSVFormatException.class, () -> {
            movieService.truncateAndSaveNewData(bytes);
        });
        String expectedMessage = "Formato do CSV Inválido";
        assertTrue(expectedMessage.equalsIgnoreCase(invalidCSVFormatException.getMessage()));
    }


}
