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
import raspberry.awards.api.persistency.*;
import raspberry.awards.api.service.dto.TopWinnersDTO;
import raspberry.awards.api.service.dto.WinnerDTO;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final CsvMapper<Movie, MovieCsvRegistry> mapper;
    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;

    public void saveAllMovies(byte[] csvBytes) throws InvalidCSVFormatException {
        List<MovieCsvRegistry> movieCsvRegistries = byteArrayToListMovies(csvBytes);
        List<Movie> movies = fromCsvToMovies(movieCsvRegistries);
        Assert.notEmpty(movies, "Movies list cannot be null");

        for (Movie movie : movies) {
            Set<Producer> producerSet = movie.getProducers().stream()
                    .map(p ->
                            producerRepository.findByNameIgnoreCase(p.getName())
                            .orElseGet(() ->
                                    producerRepository.save(new Producer(p.getName()))
                            ))
                    .collect(Collectors.toSet());

            movie.setProducers(producerSet);
        }

        movieRepository.saveAll(movies);
    }

    public TopWinnersDTO findAllGreatestAndQuicklyWinners(){
        Set<WinnerDTO> allProducerWithLesserTimeBtWins = producerRepository.findAllProducerWithLesserAndLongTimeBtWins()
                .orElse(new HashSet<>());

        WinnerDTO minWinner = allProducerWithLesserTimeBtWins.stream()
                .min(Comparator.comparing(WinnerDTO::intervalBetweenWins))
                .orElse(new WinnerDTO(null, null, null, null));

        WinnerDTO maxWinner = allProducerWithLesserTimeBtWins.stream()
                .max(Comparator.comparing(WinnerDTO::intervalBetweenWins))
                .orElse(new WinnerDTO(null, null, null, null));

        TopWinnersDTO dto = new TopWinnersDTO(
                List.of(minWinner),
                List.of(maxWinner));
        return dto;
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
        CsvToBean<MovieCsvRegistry> csvToBuilder = new CsvToBeanBuilder<MovieCsvRegistry>(reader)
                .withMappingStrategy(strategy)
                .withSeparator(';')
                .build();
        try {
            List<MovieCsvRegistry> movieCsvRegistries = csvToBuilder.parse();
            Assert.notEmpty(movieCsvRegistries, "Movies csv cannot be null");
            IOUtils.closeQuietly(bais, reader);
            return movieCsvRegistries;
        } catch (RuntimeException e) {

            throw new InvalidCSVFormatException(e.getCause());
        }
    }
}
