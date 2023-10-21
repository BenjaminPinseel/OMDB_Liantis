package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder()
public class MovieShortResponseDto {
    private String Title;
    private String year;
    private String genre;
    private String director;
    private String actors;
    private String language;
    private String country;
    private Set<RatingDto> ratings;
    private String type;
    private String plot;
}
