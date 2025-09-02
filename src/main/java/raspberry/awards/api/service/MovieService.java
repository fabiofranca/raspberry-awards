package raspberry.awards.api.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import raspberry.awards.api.InvalidCSVFormatException;
import raspberry.awards.api.mapper.CsvMapper;
import raspberry.awards.api.mapper.MovieCsvRegistry;
import raspberry.awards.api.persistency.Movie;
import raspberry.awards.api.persistency.MovieRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final CsvMapper<Movie, MovieCsvRegistry> mapper;
    private final MovieRepository movieRepository;

    public List<Movie> truncateAndSaveNewData(byte[] csvBytes) throws InvalidCSVFormatException {
        List<MovieCsvRegistry> movieCsvRegistries = byteArrayToListMovies(csvBytes);
        List<Movie> movies = fromCsvToMovies(movieCsvRegistries);
        Assert.notEmpty(movies, "Movies list cannot be null");
        movieRepository.deleteAll();
        return movieRepository.saveAll(movies);
    }

    public void simpleSave(byte[] csvBytes) throws InvalidCSVFormatException {
        List<MovieCsvRegistry> movieCsvRegistries = byteArrayToListMovies(csvBytes);
        List<Movie> movies = fromCsvToMovies(movieCsvRegistries);
        Assert.notEmpty(movies, "Movies list cannot be null");
        movieRepository.saveAll(movies);
    }

    public List<Award> findAllLongestIntervalBetweenWins() {
        return movieRepository.findAllLongestIntervalBetweenWins();
    }

    private List<Movie> fromCsvToMovies(List<MovieCsvRegistry> movieCsvRegistries) {
        return movieCsvRegistries.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<MovieCsvRegistry> byteArrayToListMovies(byte[] bytes) throws InvalidCSVFormatException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        InputStreamReader reader = new InputStreamReader(bais, StandardCharsets.UTF_8);
        HeaderColumnNameMappingStrategy<MovieCsvRegistry> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(MovieCsvRegistry.class);
        CsvToBean<MovieCsvRegistry> moviesCsv = new CsvToBeanBuilder<MovieCsvRegistry>(reader)
                .withMappingStrategy(strategy)
                .withSeparator(';')
                .build();
        try {
            List<MovieCsvRegistry> movieCsvRegistries = moviesCsv.parse();
            Assert.notEmpty(movieCsvRegistries, "Movies csv cannot be null");
            IOUtils.closeQuietly(bais, reader);
            return movieCsvRegistries;
        } catch (RuntimeException e) {

            throw new InvalidCSVFormatException(e.getCause());
        }
    }
}
