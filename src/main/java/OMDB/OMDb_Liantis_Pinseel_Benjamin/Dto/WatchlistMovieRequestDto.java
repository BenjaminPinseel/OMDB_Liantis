package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import lombok.Data;

@Data
public class WatchlistMovieRequestDto {
    private String userId;
    private String movieId;
    private String watchlistId;
}
