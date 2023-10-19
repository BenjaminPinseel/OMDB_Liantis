package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WatchlistResponseDto {
    private String title;
    private String description;
    private List<MovieDto> movieDtos;

}
