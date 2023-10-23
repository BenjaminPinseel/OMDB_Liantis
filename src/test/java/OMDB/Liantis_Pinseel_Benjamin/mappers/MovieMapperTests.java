package OMDB.Liantis_Pinseel_Benjamin.mappers;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieMapperTests {

    private final MovieMapper movieMapper = new MovieMapper();

    @Test
    void testMapToPageDtoDetailed() {
        Movie movie = Movie.builder()
                .title("Inception")
                .year("2020")
                .director("Christopher Nolan")
                .actors("Leonardo DiCaprio")
                .genre("Action")
                .language("English")
                .country("USA")
                .runtime("2h 28min")
                .rated("PG-13")
                .plot("Great Movie")
                .poster("https://inception.com")
                .ratingDtos(new HashSet<>())
                .build();

        Page<Movie> moviePage = new PageImpl<>(Collections.singletonList(movie));

        PageDto<MovieResponseDto> result = movieMapper.mapToPageDtoDetailed(moviePage);
        assertEquals(1, result.getTotalElements());
        assertEquals(movie.getTitle(), result.getResponseDtos().iterator().next().getTitle());
        assertEquals(movie.getYear(), result.getResponseDtos().iterator().next().getYear());
        assertEquals(movie.getDirector(), result.getResponseDtos().iterator().next().getDirector());
        assertEquals(movie.getActors(), result.getResponseDtos().iterator().next().getActors());
        assertEquals(movie.getGenre(), result.getResponseDtos().iterator().next().getGenre());
        assertEquals(movie.getLanguage(), result.getResponseDtos().iterator().next().getLanguage());
        assertEquals(movie.getCountry(), result.getResponseDtos().iterator().next().getCountry());
        assertEquals(movie.getRuntime(), result.getResponseDtos().iterator().next().getRuntime());
        assertEquals(movie.getRated(), result.getResponseDtos().iterator().next().getRated());
        assertEquals(movie.getPlot(), result.getResponseDtos().iterator().next().getPlot());
        assertEquals(movie.getPoster(), result.getResponseDtos().iterator().next().getPoster());
    }

    @Test
    void testMapToPageDtoShort() {
        Movie movie = Movie.builder()
                .title("Titanic")
                .year("1997")
                .director("James Cameron")
                .actors("Leonardo DiCaprio, Kate Winslet")
                .genre("Romance")
                .language("English")
                .country("USA")
                .runtime("3h 14min")
                .rated("PG-13")
                .plot("A classic romance")
                .poster("https://titanic.com")
                .ratingDtos(new HashSet<>())
                .build();

        Page<Movie> moviePage = new PageImpl<>(Collections.singletonList(movie));

        PageDto<MovieResponseDto> result = movieMapper.mapToPageDtoShort(moviePage);
        assertEquals(1, result.getTotalElements());
        assertEquals(movie.getTitle(), result.getResponseDtos().iterator().next().getTitle());
        assertEquals(movie.getYear(), result.getResponseDtos().iterator().next().getYear());
        assertEquals(movie.getDirector(), result.getResponseDtos().iterator().next().getDirector());
        assertEquals(movie.getActors(), result.getResponseDtos().iterator().next().getActors());
        assertEquals(movie.getGenre(), result.getResponseDtos().iterator().next().getGenre());
        assertEquals(movie.getLanguage(), result.getResponseDtos().iterator().next().getLanguage());
        assertEquals(movie.getCountry(), result.getResponseDtos().iterator().next().getCountry());
        assertEquals(movie.getRated(), result.getResponseDtos().iterator().next().getRated());
        assertEquals(movie.getPlot(), result.getResponseDtos().iterator().next().getPlot());
    }

    @Test
    void testMapMovieToMovieShortResponseDto() {
        Movie movie = Movie.builder()
                .title("Inception")
                .year("2010")
                .rated("PG-13")
                .released("16 Jul 2010")
                .runtime("148 min")
                .genre("Action, Adventure, Sci-Fi")
                .director("Christopher Nolan")
                .writer("Christopher Nolan")
                .actors("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page")
                .plot("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .language("English, Japanese, French")
                .country("United States, United Kingdom")
                .poster("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg")
                .ratingDtos(new HashSet<>())
                .build();

        MovieResponseDto movieShortResponseDto = movieMapper.mapMovieToMovieShortResponseDto(movie);
        assertEquals(movie.getTitle(), movieShortResponseDto.getTitle());
        assertEquals(movie.getYear(), movieShortResponseDto.getYear());
        assertEquals(movie.getRated(), movieShortResponseDto.getRated());
        assertEquals(movie.getGenre(), movieShortResponseDto.getGenre());
        assertEquals(movie.getDirector(), movieShortResponseDto.getDirector());
        assertEquals(movie.getActors(), movieShortResponseDto.getActors());
        assertEquals(movie.getLanguage(), movieShortResponseDto.getLanguage());
        assertEquals(movie.getCountry(), movieShortResponseDto.getCountry());
        assertEquals(movie.getPlot(), movieShortResponseDto.getPlot());
    }

    @Test
    void testMapMovieToDetailedMovieResponseDto() {
        Movie movie = Movie.builder()
                .title("Inception")
                .year("2010")
                .rated("PG-13")
                .released("16 Jul 2010")
                .runtime("148 min")
                .genre("Action, Adventure, Sci-Fi")
                .director("Christopher Nolan")
                .writer("Christopher Nolan")
                .actors("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page")
                .plot("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .language("English, Japanese, French")
                .country("United States, United Kingdom")
                .poster("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg")
                .ratingDtos(new HashSet<>())
                .build();

        MovieResponseDto movieDetailedResponseDto = movieMapper.mapMovieToDetailedMovieResponseDto(movie);
        assertEquals(movie.getTitle(), movieDetailedResponseDto.getTitle());
        assertEquals(movie.getYear(), movieDetailedResponseDto.getYear());
        assertEquals(movie.getRated(), movieDetailedResponseDto.getRated());
        assertEquals(movie.getGenre(), movieDetailedResponseDto.getGenre());
        assertEquals(movie.getDirector(), movieDetailedResponseDto.getDirector());
        assertEquals(movie.getWriter(), movieDetailedResponseDto.getWriter());
        assertEquals(movie.getActors(), movieDetailedResponseDto.getActors());
        assertEquals(movie.getLanguage(), movieDetailedResponseDto.getLanguage());
        assertEquals(movie.getCountry(), movieDetailedResponseDto.getCountry());
        assertEquals(movie.getRuntime(), movieDetailedResponseDto.getRuntime());
        assertEquals(movie.getPlot(), movieDetailedResponseDto.getPlot());
        assertEquals(movie.getPoster(), movieDetailedResponseDto.getPoster());
    }


}
