package raspberry.awards.api.mapper;

import org.springframework.stereotype.Service;
import raspberry.awards.api.persistency.Movie;
import raspberry.awards.api.persistency.Producer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieMapper implements CsvMapper<Movie, MovieCsvRegistry> {

    @Override
    public Movie mapTo(MovieCsvRegistry movieCsvRegistry) {
        return Movie.builder()
                .releasedYear(movieCsvRegistry.getYear())
                .title(movieCsvRegistry.getTitle())
                .studios(movieCsvRegistry.getStudios())
                .winner(movieCsvRegistry.getWinner())
                .producers(producerStringToList(
                        movieCsvRegistry.getProducers()))
                .build();
    }

    private Set<Producer> producerStringToList(List<String> producersName) {
        return producersName.stream()
                .map(Producer::new)
                .collect(Collectors.toSet());
    }

    @Override
    public MovieCsvRegistry mapFrom(Movie movie) {
        return MovieCsvRegistry.builder()
                .year(movie.getReleasedYear())
//                .producers(movie.getProducers())
                .studios(movie.getStudios())
                .title(movie.getTitle())
                .winner(movie.getWinner())
                .build();
    }
}
