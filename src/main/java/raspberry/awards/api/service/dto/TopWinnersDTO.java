package raspberry.awards.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record TopWinnersDTO(

        @Schema(description = "Lista dos ganhadores com menor intervalo em anos entre vitórias")
        List<WinnerDTO> max,

        @Schema(description = "Lista dos ganhadores com maior intervalo em anos entre vitórias")
        List<WinnerDTO> min
) {
}
