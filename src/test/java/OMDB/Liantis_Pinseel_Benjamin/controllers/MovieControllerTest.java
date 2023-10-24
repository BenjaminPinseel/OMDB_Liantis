package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieController movieController;

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
        when(movieService.findById(movie.getImdbID())).thenReturn(movieResponseDto);

        // Act
        MovieResponseDto result = movieController.findById(movie.getImdbID());

        // Assert
        assertEquals(movieResponseDto, result);
    }

    // Test for finding a movie by title
    @Test
    void findByTitleTest() {
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
        when(movieService.findByTitle(movie.getTitle(), movie.getType(), Integer.parseInt(movie.getYear()), "short","json")).thenReturn(movieResponseDto);

        // Act
        MovieResponseDto result = movieController.findByTitle(movie.getTitle(), movie.getType(), Integer.parseInt(movie.getYear()), "short","json");

        // Assert
        assertEquals(movieResponseDto, result);
    }

    // Test for finding all movies
    @Test
    void findAllTest() {
        // Arrange
        String title = "Test Title";
        String type = "movie";
        Integer year = 2021;
        int page = 1;

        Set<MovieResponseDto> movieResponseDtos = new HashSet<>();
        PageDto<MovieResponseDto> movieResponsePageDto = new PageDto<MovieResponseDto>(1, 1, movieResponseDtos);
        when(movieService.findAll(anyString(), anyString(), anyInt(), anyInt())).thenReturn(movieResponsePageDto);

        // Act
        PageDto<MovieResponseDto> result = movieController.findAll(title, type, year, page);

        // Assert
        assertEquals(movieResponseDtos, result.getResponseDtos());
    }
}
