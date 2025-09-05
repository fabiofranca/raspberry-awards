package raspberry.awards.api.persistency;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(name = "idx_movie_winner", columnList = "winner"))
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private Integer releasedYear;
    private String title;
    private String studios;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_producer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<Producer> producers;
    private String winner;
}

