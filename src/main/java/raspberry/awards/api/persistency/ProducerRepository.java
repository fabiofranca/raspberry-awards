package raspberry.awards.api.persistency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByNameIgnoreCase(String name);

    @Query("""
            SELECT p.name as producerName,
              max(m.releasedYear) - min(m.releasedYear) as intervalBetweenWins,
              m.releasedYear,
              m.releasedYear
            FROM Producer as p
              join p.movies as m
            where m.winner = 'yes'
            group by p.name, m.releasedYear
            having count(1) > 1
            order by 2 desc
            """)
    Optional<Set<WinnersDTO>> findAllProducersWithMoreThanOneWin();


    @Query("""
            select t1.producerName,
                 abs(min(t1.releasedYear - t2.releasedYear)) as intervalBetweenWins,
                 t1.releasedYear as previousWin,
                 t2.releasedYear as followingWin
            from(
               SELECT  m.releasedYear as releasedYear,
                       p.id as producerId,
                       p.name as producerName
               FROM Movie m
               join m.producers p
               where m.winner = 'yes'
               order by p.id) as t1
            join (
               SELECT m.releasedYear as releasedYear,
                       p.id as producerId,
                       p.name as producerName
               FROM Movie m
               join m.producers p
               where m.winner ='yes'
               order by m.id) as t2 on t1.producerId = t2.producerId and t1.releasedYear < t2.releasedYear
            group by t1.releasedYear, t1.producerId
            order by 2
            """)
    Optional<Set<WinnersDTO>> findAllProducerWithLesserTimeBtWins();
}
