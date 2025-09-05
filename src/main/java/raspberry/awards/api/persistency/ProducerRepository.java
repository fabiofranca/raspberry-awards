package raspberry.awards.api.persistency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByNameIgnoreCase(String name);
}
