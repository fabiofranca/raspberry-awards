package raspberry.awards.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import raspberry.awards.api.InvalidCSVFormatException;
import raspberry.awards.api.service.Award;
import raspberry.awards.api.service.MovieService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @Operation(summary = "listar os produtores com maior intervalo entre os premios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Award.class)))
    })
    @GetMapping(path = "/longest-interval-between-wins")
    public List<Award> getAllLongestIntervalBetweenWins(){
        return movieService.findAllLongestIntervalBetweenWins();
    }

    @Operation(summary = "Upload do csv com a lista de filmes indicados ao premio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Upload realizado e csv valido"),
            @ApiResponse(responseCode = "400", description = "CSV inválido")
    })
    @PostMapping(path = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCSV(@Validated @RequestBody MultipartFile csvFile) {
        try {
            //TODO faltou melhorar o tratamento de exceptions, descrevendo melhor os erros do csv
            movieService.truncateAndSaveNewData(csvFile.getBytes());
        } catch (IOException | InvalidCSVFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
