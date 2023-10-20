package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieDto {

    private String imdbID;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private int year;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    private Date released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private URL poster;

    @JsonProperty("Ratings")
    private List<RatingDto> ratingDtos;

    @JsonProperty("Metascore")
    private int metascore;

    private float imdbRating;

    private long imdbVotes;

    @JsonProperty("Type")
    private String type;

    private Date DVD;

    @JsonProperty("BoxOffice")
    private String boxOffice;

    @JsonProperty("Production")
    private String production;

    @JsonProperty("Website")
    private URL website;

    @JsonProperty("Response")
    private boolean response;
}
