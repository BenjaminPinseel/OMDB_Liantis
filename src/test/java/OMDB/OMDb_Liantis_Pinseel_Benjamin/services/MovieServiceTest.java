package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieListDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @Mock
    private EncryptionUtils encryptionUtils;

    @Mock
    private MovieClient movieClient;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieService movieService;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for finding a movie by ID
    @Test
    void findByIdTest() {
        // Arrange
        String id = "123";
        String type = "movie";
        int year = 2021;
        String plot = "full";
        String returnType = "json";

        Movie movie = Movie.builder()
                .imdbID("123")
                .build();
        when(movieClient.findById(anyString(), anyString(), anyString(), anyInt(), anyString(), anyString())).thenReturn(movie);
        when(movieMapper.mapMovieToDetailedMovieResponseDto(movie)).thenReturn(new MovieResponseDto());

        // Act
        MovieResponseDto result = movieService.findById(id, type, year, plot, returnType);

        // Assert
        assertEquals(MovieResponseDto.class, result.getClass());
    }

    // Test for finding a movie by title
    @Test
    void findByTitleTest() {
        // Arrange
        String title = "Test Title";
        String type = "movie";
        int year = 2021;
        String plot = "full";
        String returnType = "json";

        Movie movie = Movie.builder()
                .title("Shrek")
                .build();
        when(movieClient.findByTitle(anyString(), anyString(), anyString(), anyInt(), anyString(), anyString())).thenReturn(movie);
        when(movieMapper.mapMovieToDetailedMovieResponseDto(movie)).thenReturn(new MovieResponseDto());

        // Act
        MovieResponseDto result = movieService.findByTitle(title, type, year, plot, returnType);

        // Assert
        assertEquals(MovieResponseDto.class, result.getClass());
    }

    // Test for finding all movies
    @Test
    void findAllTest() {
        String title = "Test Title";
        String type = "movie";
        int year = 2021;
        int page = 1;

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setSearch(new ArrayList<>());
        when(movieClient.findAll(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(movieListDto);
        when(movieMapper.mapMovieToMovieShortResponseDto(any())).thenReturn(new MovieResponseDto());

        // Act
        Set<MovieResponseDto> result = movieService.findAll(title, type, year, page);

        // Assert
        assertEquals(HashSet.class, result.getClass());
    }
}
