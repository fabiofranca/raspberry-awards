package raspberry.awards.api.persistency;

public record WinnersDTO(
        String producerName,
        int intervalBetweenWins,
        int previousWin,
        int followingWin
) {
}
