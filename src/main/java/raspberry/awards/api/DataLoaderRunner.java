package raspberry.awards.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import raspberry.awards.api.service.MovieService;

import java.io.IOException;
import java.io.InputStream;

@Component
@Profile({"dev", "prod"})
@RequiredArgsConstructor
@Slf4j
public class DataLoaderRunner {

    private final MovieService movieService;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady(){
        log.info("DataLoader running...");

        ClassPathResource csvResource = new ClassPathResource("data/movielist.csv");
        log.debug("Arquivo da lista de filmes carregado");

        try(InputStream is = csvResource.getInputStream()){
            movieService.simpleAllMovies(is.readAllBytes());
            log.debug("Banco populado");
        } catch (IOException | InvalidCSVFormatException e) {
            log.error("Erro ao salvar arquivo csv", e);
            throw new RuntimeException(e);
        }

        log.info("DataLoader done!");

    }
}
