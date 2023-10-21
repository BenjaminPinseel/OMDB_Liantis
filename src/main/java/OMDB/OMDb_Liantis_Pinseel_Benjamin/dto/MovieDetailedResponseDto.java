package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder()
public class MovieDetailedResponseDto {
    private String Title;
    private String year;
    private String genre;
    private String director;
    private String actors;
    private String language;
    private String country;
    private Set<RatingDto> ratings;
    private String type;
    private String runtime;
    private String plot;
    private String rated;
    private String writer;
    private String poster;
}
