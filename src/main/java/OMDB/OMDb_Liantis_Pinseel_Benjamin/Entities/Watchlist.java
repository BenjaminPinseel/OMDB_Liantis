package OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.MovieDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Table(schema = "omdb")
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

    @ElementCollection(targetClass =  String.class)
    @CollectionTable(name = "watchlist_movie", joinColumns = @JoinColumn(name = "watchlist_id"))
    @JoinColumn(name = "movie_id")
    private List<String> movieIds;


    public static class WatchlistBuilder{
        public String title;
        public String description;


        public Watchlist build(){
            return new Watchlist(null,title,description, null);
        }
    }
}
