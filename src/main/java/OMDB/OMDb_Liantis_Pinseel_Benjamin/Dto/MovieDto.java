package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieDto {

    private String imdbID;

    private String Title;

    private int Year;

    private String Rated;

    private Date Released;

    private String Runtime;

    private String Genre;

    private String Director;

    private String Writer;

    private String Actors;

    private String Plot;

    private String Language;

    private String Country;

    private String Awards;

    private URL Poster;

    private List<RatingDto> ratingDtos;

    private int Metascore;

    private float imdbRating;

    private long imdbVotes;

    private String Type;

    private Date DVD;

    private String BoxOffice;

    private String Production;

    private URL Website;

    private Boolean Response;
}