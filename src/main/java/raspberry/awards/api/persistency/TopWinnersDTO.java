package raspberry.awards.api.persistency;

import java.util.List;

public record TopWinnersDTO(
        List<WinnerDTO> max,
        List<WinnerDTO> min
) {
}
