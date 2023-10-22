package OMDB.OMDb_Liantis_Pinseel_Benjamin.controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.services.MovieService;
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
        String id = "123";
        String type = "movie";
        int year = 2021;
        String plot = "full";
        String returnType = "json";

        MovieResponseDto movieResponseDto = new MovieResponseDto();
        when(movieService.findById(anyString(), anyString(), anyInt(), anyString(), anyString())).thenReturn(movieResponseDto);

        // Act
        MovieResponseDto result = movieController.findById(id, type, year, plot, returnType);

        // Assert
        assertEquals(movieResponseDto, result);
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

        MovieResponseDto movieResponseDto = new MovieResponseDto();
        when(movieService.findByTitle(anyString(), anyString(), anyInt(), anyString(), anyString())).thenReturn(movieResponseDto);

        // Act
        MovieResponseDto result = movieController.findByTitle(title, type, year, plot, returnType);

        // Assert
        assertEquals(movieResponseDto, result);
    }

    // Test for finding all movies
    @Test
    void findAllTest() {
        // Arrange
        String title = "Test Title";
        String type = "movie";
        int year = 2021;
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
