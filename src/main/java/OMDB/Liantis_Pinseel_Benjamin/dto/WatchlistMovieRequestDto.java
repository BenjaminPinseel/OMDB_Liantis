package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.Data;

@Data
public class WatchlistMovieRequestDto {
    private String userId;
    private String movieId;
    private String watchlistId;
}
