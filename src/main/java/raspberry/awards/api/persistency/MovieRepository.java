package raspberry.awards.api.persistency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import raspberry.awards.api.service.Award;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    //TODO refazer a query usando a tabela associativa producers/movie
    @Query("""
            SELECT max(m.releasedYear) - min(m.releasedYear) as interval,
                p.name as producer,
                max(m.releasedYear) as previousWin,
                min(m.releasedYear) as followingWin
            FROM Movie as m
              join m.producers as p
            WHERE m.winner = 'yes'
            group by p.name
            order by interval desc
            """)
    List<Award> findAllLongestIntervalBetweenWins();
}
