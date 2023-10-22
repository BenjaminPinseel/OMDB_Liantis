package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieListDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String id = "123";
        String type = "movie";
        int year = 2021;
        String plot = "full";
        String returnType = "json";

        Movie movie = Movie.builder()
                .imdbID("123")
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
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
        when(encryptionUtils.decrypt(any())).thenReturn("1");
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
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(movieClient.findAll(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(movieListDto);

        // Act
        PageDto<MovieResponseDto> result = movieService.findAll(title, type, year, page);

        // Assert
        assertEquals(HashSet.class, result.getResponseDtos().getClass());
    }
}
