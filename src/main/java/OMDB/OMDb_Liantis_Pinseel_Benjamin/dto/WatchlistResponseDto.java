package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class WatchlistResponseDto {
    private String title;
    private String description;
    private String userId;
    private Set<MovieResponseDto> movies;

}
