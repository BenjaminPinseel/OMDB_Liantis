package OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder (builderClassName = "WatchlistBuilder")
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

    private List<Movie> movies;

    public static class WatchlistBuilder{
        public String title;
        public String description;


        public Watchlist build(){
            return new Watchlist(null,title,description, null);
        }
    }
}
