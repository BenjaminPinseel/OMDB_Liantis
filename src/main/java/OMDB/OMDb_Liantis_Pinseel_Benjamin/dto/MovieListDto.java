package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieListDto {

    @JsonProperty("Search")
    private List<Movie> search;

    private int totalResults;

    @JsonProperty("Response")
    private boolean response;
}
