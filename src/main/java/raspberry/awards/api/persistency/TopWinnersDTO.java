package raspberry.awards.api.persistency;

import java.util.Set;

public record TopWinnersDTO(
        Set<WinnersDTO> max,
        Set<WinnersDTO> min
) {
}
