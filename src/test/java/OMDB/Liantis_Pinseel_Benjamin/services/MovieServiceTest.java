package OMDB.Liantis_Pinseel_Benjamin.services;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieListDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    EncryptionUtils encryptionUtils;

    @Mock
    MovieClient movieClient;

    @Mock
    MovieMapper movieMapper;

    @InjectMocks
    MovieService movieService;

    @Value("${encrypted.api.key}")
    String encryptedApiKey;


    // Test for finding a movie by ID
    @Test
    void findByIdTest() {
        // Arrange
        Movie movie = Movie.builder()
                .title("test title")
                .type("movie")
                .plot("test plot")
                .actors("henk")
                .country("USA")
                .language("English")
                .poster("")
                .year("2001")
                .imdbID("123")
                .response("True")
                .build();
        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .title(movie.getTitle())
                .type(movie.getType())
                .plot(movie.getPlot())
                .actors(movie.getActors())
                .country(movie.getCountry())
                .language(movie.getLanguage())
                .poster(movie.getPoster())
                .year(movie.getYear())
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(movieClient.findById(anyString(), anyString())).thenReturn(movie);
        when(movieMapper.mapMovieToDetailedMovieResponseDto(movie)).thenReturn(movieResponseDto);

        // Act
        MovieResponseDto result = movieService.findById(movie.getImdbID());

        // Assert
        assertEquals(MovieResponseDto.class, result.getClass());
        assertEquals(movie.getTitle(), result.getTitle());
        assertEquals(movie.getYear(), result.getYear());
        assertEquals(movie.getGenre(), result.getGenre());
        assertEquals(movie.getDirector(), result.getDirector());
        assertEquals(movie.getActors(), result.getActors());
        assertEquals(movie.getLanguage(), result.getLanguage());
        assertEquals(movie.getCountry(), result.getCountry());
        assertEquals(movie.getType(), result.getType());
        assertEquals(movie.getRuntime(), result.getRuntime());
        assertEquals(movie.getPlot(), result.getPlot());
        assertEquals(movie.getRated(), result.getRated());
        assertEquals(movie.getWriter(), result.getWriter());
        assertEquals(movie.getPoster(), result.getPoster());
    }

    // Test for finding a movie by title
    @Test
    void findByTitleTest() {
        // Arrange
        String returnType = "json";

        Movie movie = Movie.builder()
                .title("Shrek")
                .year("2001")
                .genre("Animation, Adventure, Comedy")
                .director("Andrew Adamson, Vicky Jenson")
                .actors("Mike Myers, Eddie Murphy, Cameron Diaz, John Lithgow")
                .language("English")
                .country("USA")
                .type("movie")
                .runtime("1h 30min")
                .plot("After his swamp is filled with magical creatures, Shrek agrees to rescue Princess Fiona for a villainous lord in order to get his land back.")
                .rated("PG")
                .writer("William Steig (based upon the book by), Ted Elliott, Terry Rossio, Joe Stillman, Roger S.H. Schulman, Cody Cameron, Chris Miller, Conrad Vernon")
                .poster("https://m.media-amazon.com/images/M/MV5BMTYwOTMwNjk4OF5BMl5BanBnXkFtZTgwMTkxNjMwMDE@._V1_SX300.jpg")
                .response("True")
                .build();
        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .rated(movie.getRated())
                .runtime(movie.getRuntime())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .writer(movie.getWriter())
                .actors(movie.getActors())
                .plot(movie.getPlot())
                .language(movie.getLanguage())
                .country(movie.getCountry())
                .poster(movie.getPoster())
                .type(movie.getType())
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(movieClient.findByTitle(anyString(), anyString(), anyString(), anyInt(), anyString(), anyString())).thenReturn(movie);
        when(movieMapper.mapMovieToDetailedMovieResponseDto(movie)).thenReturn(movieResponseDto);

        // Act
        MovieResponseDto result = movieService.findByTitle(movie.getTitle(), "movie", 2009, "short", returnType);

        // Assert
        assertEquals(MovieResponseDto.class, result.getClass());
        assertEquals(movie.getTitle(), result.getTitle());
        assertEquals(movie.getYear(), result.getYear());
        assertEquals(movie.getGenre(), result.getGenre());
        assertEquals(movie.getDirector(), result.getDirector());
        assertEquals(movie.getActors(), result.getActors());
        assertEquals(movie.getLanguage(), result.getLanguage());
        assertEquals(movie.getCountry(), result.getCountry());
        assertEquals(movie.getType(), result.getType());
        assertEquals(movie.getRuntime(), result.getRuntime());
        assertEquals(movie.getPlot(), result.getPlot());
        assertEquals(movie.getRated(), result.getRated());
        assertEquals(movie.getWriter(), result.getWriter());
        assertEquals(movie.getPoster(), result.getPoster());

    }

    // Test for finding all movies
    @Test
    void findAllTest() {
        Movie movie = Movie.builder()
                .title("Inception")
                .year("2021")
                .rated("PG-13")
                .runtime("2h 28min")
                .genre("Action, Adventure, Sci-Fi")
                .director("Christopher Nolan")
                .writer("Christopher Nolan")
                .actors("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page")
                .plot("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.")
                .language("English")
                .country("USA")
                .poster("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg")
                .type("movie")
                .build();


        List<Movie> search = new ArrayList<>();
        search.add(movie);

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setSearch(search);
        movieListDto.setTotalResults(1);
        movieListDto.setResponse(true);

        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .rated(movie.getRated())
                .runtime(movie.getRuntime())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .writer(movie.getWriter())
                .actors(movie.getActors())
                .plot(movie.getPlot())
                .language(movie.getLanguage())
                .country(movie.getCountry())
                .poster(movie.getPoster())
                .type(movie.getType())
                .build();

        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(movieClient.findAll(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(movieListDto);
        when(movieMapper.mapMovieToMovieShortResponseDto(any(Movie.class))).thenReturn(movieResponseDto);

        // Act
        PageDto<MovieResponseDto> result = movieService.findAll(movie.getTitle(), movie.getType(), Integer.parseInt(movie.getYear()), 1);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertNotNull(result.getResponseDtos());
        assertEquals(1, result.getResponseDtos().size());

        MovieResponseDto testMovieResponseDto = result.getResponseDtos().iterator().next();
        assertNotNull(testMovieResponseDto);
        assertEquals(movie.getTitle(), testMovieResponseDto.getTitle());
        assertEquals(movie.getYear(), testMovieResponseDto.getYear());
        assertEquals(movie.getRated(), testMovieResponseDto.getRated());
        assertEquals(movie.getRuntime(), testMovieResponseDto.getRuntime());
        assertEquals(movie.getGenre(), testMovieResponseDto.getGenre());
        assertEquals(movie.getDirector(), testMovieResponseDto.getDirector());
        assertEquals(movie.getWriter(), testMovieResponseDto.getWriter());
        assertEquals(movie.getActors(), testMovieResponseDto.getActors());
        assertEquals(movie.getPlot(), testMovieResponseDto.getPlot());
        assertEquals(movie.getLanguage(), testMovieResponseDto.getLanguage());
        assertEquals(movie.getCountry(), testMovieResponseDto.getCountry());
        assertEquals(movie.getPoster(), testMovieResponseDto.getPoster());
        assertEquals(movie.getType(), testMovieResponseDto.getType());

    }
}
