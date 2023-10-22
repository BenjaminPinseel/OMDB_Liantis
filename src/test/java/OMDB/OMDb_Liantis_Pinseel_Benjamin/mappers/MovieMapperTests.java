package OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
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

        assertEquals(1, movieMapper.mapToPageDtoDetailed(moviePage).getTotalElements());
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

        assertEquals(1, movieMapper.mapToPageDtoShort(moviePage).getTotalElements());
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
        assertEquals("Inception", movieShortResponseDto.getTitle());
        assertEquals("2010", movieShortResponseDto.getYear());
        assertEquals("PG-13", movieShortResponseDto.getRated());
        assertEquals("Action, Adventure, Sci-Fi", movieShortResponseDto.getGenre());
        assertEquals("Christopher Nolan", movieShortResponseDto.getDirector());
        assertEquals("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", movieShortResponseDto.getActors());
        assertEquals("English, Japanese, French", movieShortResponseDto.getLanguage());
        assertEquals("United States, United Kingdom", movieShortResponseDto.getCountry());
        assertEquals("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", movieShortResponseDto.getPlot());
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
        assertEquals("Inception", movieDetailedResponseDto.getTitle());
        assertEquals("2010", movieDetailedResponseDto.getYear());
        assertEquals("PG-13", movieDetailedResponseDto.getRated());
        assertEquals("Action, Adventure, Sci-Fi", movieDetailedResponseDto.getGenre());
        assertEquals("Christopher Nolan", movieDetailedResponseDto.getDirector());
        assertEquals("Christopher Nolan", movieDetailedResponseDto.getWriter());
        assertEquals("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", movieDetailedResponseDto.getActors());
        assertEquals("English, Japanese, French", movieDetailedResponseDto.getLanguage());
        assertEquals("United States, United Kingdom", movieDetailedResponseDto.getCountry());
        assertEquals("148 min", movieDetailedResponseDto.getRuntime());
        assertEquals("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.", movieDetailedResponseDto.getPlot());
        assertEquals("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg", movieDetailedResponseDto.getPoster());
    }


}
