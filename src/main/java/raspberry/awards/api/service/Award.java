package raspberry.awards.api.service;

public interface Award {

    String getProducer();

    Integer getInterval();

    Integer getPreviousWin();

    Integer getFollowingWin();
}
