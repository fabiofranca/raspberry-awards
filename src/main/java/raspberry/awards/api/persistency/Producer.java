package raspberry.awards.api.persistency;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_producer_name", columnList = "name"))
public class Producer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_producer",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;

    public Producer(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Producer producer)) return false;
        return Objects.equals(name, producer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
