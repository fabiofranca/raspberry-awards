package raspberry.awards.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record WinnerDTO(

        @Schema(
                description = "Nome do produtor",
                example = "Jhon Doe",
                type = "string"
        )
        String producerName,

        @Schema(
                description = "Intervalo entre vitórias em anos (sempre 3 dígitos, com zeros à esquerda)",
                example = "003",
                type = "string",
                pattern = "\\d{3}"
        )
        Integer intervalBetweenWins,

        @Schema(
                description = "Ano da vitória anterior (4 dígitos)",
                example = "1999",
                type = "string",
                pattern = "\\d{4}"
        )
        Integer previousWin,

        @Schema(
                description = "Ano da vitória seguinte (4 dígitos)",
                example = "2002",
                type = "string",
                pattern = "\\d{4}"
        )
        Integer followingWin
) {
}
