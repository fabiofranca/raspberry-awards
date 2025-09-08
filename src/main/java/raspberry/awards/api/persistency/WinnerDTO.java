package raspberry.awards.api.persistency;

public record WinnerDTO(
        String producerName,
        Integer intervalBetweenWins,
        Integer previousWin,
        Integer followingWin
) {
}
