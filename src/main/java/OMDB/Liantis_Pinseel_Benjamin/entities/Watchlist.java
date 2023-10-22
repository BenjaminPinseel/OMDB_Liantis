package OMDB.Liantis_Pinseel_Benjamin.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Table()
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder()
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Setter
    @Size(min = 1, max = 200)
    private String title;

    @Setter
    @Size(min = 1, max = 500)
    private String description;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "watchlist_movie", joinColumns = @JoinColumn(name = "watchlist_id"))
    @Column(name = "movie_id")
    @Setter
    private Set<String> movieIds;

    @Setter
    @NotNull
    private String userId;

}
