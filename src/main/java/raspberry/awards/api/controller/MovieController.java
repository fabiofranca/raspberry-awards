package raspberry.awards.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raspberry.awards.api.service.Award;
import raspberry.awards.api.service.MovieService;

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

}
