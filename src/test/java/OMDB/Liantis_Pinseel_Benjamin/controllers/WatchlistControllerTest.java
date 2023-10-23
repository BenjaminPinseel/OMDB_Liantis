package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.Liantis_Pinseel_Benjamin.services.WatchlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistControllerTest {

    @Mock
    WatchlistService watchlistService;

    @Mock
    WatchlistMapper watchlistMapper;

    @InjectMocks
    WatchlistController watchlistController;

    @Test
    void testAddMovie() {
        // Arrange
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
                .imdbID("123")
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

        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title("title 1")
                .description("description 1")
                .userId("1")
                .movies(Set.of(movieResponseDto))
                .build();
        String watchlistId = "1";
        watchlistResponseDto.setUserId(watchlistResponseDto.getUserId());
        when(watchlistService.findById(watchlistId)).thenReturn(watchlistResponseDto);
        when(watchlistService.addMovie(watchlistId, movie.getImdbID())).thenReturn(watchlistResponseDto);

        // Act
        ResponseEntity<?> result = watchlistController.addMovie(watchlistId, movie.getImdbID(), watchlistResponseDto.getUserId());

        // Assert
        assertEquals(ResponseEntity.ok(watchlistResponseDto), result);
        verify(watchlistService, times(1)).findById(watchlistId);
        verify(watchlistService, times(1)).addMovie(watchlistId, movie.getImdbID());
    }

    @Test
    void testRemoveMovie() {
        // Arrange
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
                .imdbID("123")
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

        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title("title 1")
                .description("description 1")
                .userId("1")
                .movies(Set.of(movieResponseDto))
                .build();
        String watchlistId = "1";
        watchlistResponseDto.setUserId(watchlistResponseDto.getUserId());
        when(watchlistService.findById(watchlistId)).thenReturn(watchlistResponseDto);
        when(watchlistService.removeMovie(watchlistId, movie.getImdbID())).thenReturn(watchlistResponseDto);

        // Act
        ResponseEntity<?> result = watchlistController.removeMovie(watchlistId, movie.getImdbID(), watchlistResponseDto.getUserId());

        // Assert
        assertEquals(ResponseEntity.ok(watchlistResponseDto), result);
        verify(watchlistService, times(1)).findById(watchlistId);
        verify(watchlistService, times(1)).removeMovie(watchlistId, movie.getImdbID());
    }

    @Test
    void testFindByUserId() {
        // Arrange
        List<WatchlistResponseDto> watchlistResponseDtos = new ArrayList<>();
        String userId = "user1";
        when(watchlistService.findByUserId(userId)).thenReturn(watchlistResponseDtos);

        // Act
        List<WatchlistResponseDto> result = watchlistController.findByUserId(userId);

        // Assert
        assertEquals(watchlistResponseDtos, result);
        verify(watchlistService, times(1)).findByUserId(userId);
    }

    @Test
    void testFindById() {
        // Arrange
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
                .imdbID("123")
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

        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title("title 1")
                .description("description 1")
                .userId("1")
                .movies(Set.of(movieResponseDto))
                .build();
        String id = "1";
        when(watchlistService.findById(id)).thenReturn(watchlistResponseDto);

        // Act
        WatchlistResponseDto result = watchlistController.findById(id);

        // Assert
        assertEquals(watchlistResponseDto, result);
        verify(watchlistService, times(1)).findById(id);
    }

    @Test
    void testcreate() {
        // Arrange
        WatchlistCreateDto watchlistCreateDto = WatchlistCreateDto.builder()
                .title("title")
                .description("description")
                .build();
        String userId = "user1";

        // Act
        watchlistController.create(watchlistCreateDto, userId);

        // Assert
        verify(watchlistService, times(1)).save(watchlistCreateDto, userId);
    }

    @Test
    void testUpdate() {
        // Arrange
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
                .imdbID("123")
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

        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title("title 1")
                .description("description 1")
                .userId("1")
                .movies(Set.of(movieResponseDto))
                .build();
        WatchlistUpdateRequestDto watchlistUpdateRequestDto = WatchlistUpdateRequestDto.builder()
                .id("1")
                .userId("userid")
                .title("title")
                .description("description")
                .build();
        Watchlist watchlist = Watchlist.builder()
                .title(watchlistUpdateRequestDto.getTitle())
                .userId(watchlistUpdateRequestDto.getUserId())
                .description(watchlistUpdateRequestDto.getDescription())
                .build();
        when(watchlistService.update(watchlistUpdateRequestDto)).thenReturn(watchlist);
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist)).thenReturn(watchlistResponseDto);

        // Act
        WatchlistResponseDto result = watchlistController.update(watchlistUpdateRequestDto);

        // Assert
        assertEquals(watchlistResponseDto, result);
        verify(watchlistService, times(1)).update(watchlistUpdateRequestDto);
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(watchlist);
    }

    @Test
    void testDelete() {
        // Arrange
        String id = "1";

        // Act
        watchlistController.delete(id);

        // Assert
        verify(watchlistService, times(1)).deleteById(id);
    }

}
