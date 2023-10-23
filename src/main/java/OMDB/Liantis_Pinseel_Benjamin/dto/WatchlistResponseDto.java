package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
public class WatchlistResponseDto {
    private String title;
    private String description;
    private String userId;
    private Set<MovieResponseDto> movies;

}
