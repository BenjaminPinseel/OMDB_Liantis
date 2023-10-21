package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class WatchlistResponseDto {
    private String title;
    private String description;
    private String userId;
    private Set<MovieShortResponseDto> movies;

}
