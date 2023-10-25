package OMDB.Liantis_Pinseel_Benjamin.services;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import OMDB.Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.Liantis_Pinseel_Benjamin.repositories.WatchlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTest {

    @Mock
    WatchlistRepository watchlistRepository;

    @Mock
    WatchlistMapper watchlistMapper;

    @Mock
    MovieClient movieClient;

    @Mock
    MovieMapper movieMapper;

    @Mock
    EncryptionUtils encryptionUtils;

    @InjectMocks
    WatchlistService watchlistService;

    // Test for finding an existing watchlist by ID
    @Test
    public void testFindById_ExistingWatchlist_ReturnsWatchlistResponseDto() {
        // Arrange
        Watchlist watchlist = Watchlist.builder()
                .id("id")
                .title("test title watchlist")
                .description("test description")
                .movieIds(Set.of("123"))
                .userId("userid")
                .build();
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
        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title(watchlist.getTitle())
                .description(watchlist.getDescription())
                .userId(watchlist.getUserId())
                .movies(Set.of(movieResponseDto))
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findById(watchlist.getId())).thenReturn(Optional.of(watchlist));
        when(movieClient.findById(anyString(), eq(movie.getImdbID()))).thenReturn(movie);
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(watchlistResponseDto);

        // Act
        WatchlistResponseDto result = watchlistService.findById("id");

        // Assert
        assertEquals(WatchlistResponseDto.class, result.getClass());
        verify(watchlistRepository, times(1)).findById(eq(watchlist.getId()));
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(eq(watchlist));
        assertEquals(result.getTitle(), watchlistResponseDto.getTitle());
        assertEquals(result.getDescription(), watchlistResponseDto.getDescription());
        assertEquals(result.getUserId(), watchlistResponseDto.getUserId());
        assertEquals(result.getMovies().size(), watchlistResponseDto.getMovies().size());
    }

    // Test for finding a non-existing watchlist by ID
    @Test
    public void testFindById_NonExistingWatchlist_ThrowsResourceNotFoundException() {
        // Arrange
        when(watchlistRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> watchlistService.findById("id"));


    }

    // Test for saving a watchlist
    @Test
    public void testSave() {
        // Arrange
        WatchlistCreateDto watchlistCreateDto = WatchlistCreateDto.builder()
                .title("test title")
                .description("test description")
                .build();
        String userId = "userId";
        Watchlist watchlist = Watchlist.builder()
                .title(watchlistCreateDto.getTitle())
                .description(watchlistCreateDto.getDescription())
                .userId(userId)
                .build();

        // Act
        watchlistService.save(watchlistCreateDto, userId);


        // Assert
        ArgumentCaptor<Watchlist> watchlistArgumentCaptor = ArgumentCaptor.forClass(Watchlist.class);
        verify(watchlistRepository, times(1)).save(watchlistArgumentCaptor.capture());
        Watchlist capturedWatchlist = watchlistArgumentCaptor.getValue();
        assertEquals(watchlistCreateDto.getTitle(), capturedWatchlist.getTitle());
        assertEquals(watchlistCreateDto.getDescription(), capturedWatchlist.getDescription());
        assertEquals(userId, capturedWatchlist.getUserId());
    }

    // Test for updating a watchlist
    @Test
    public void testUpdate() {
        // Arrange
        WatchlistUpdateRequestDto watchlistUpdateRequestDto = WatchlistUpdateRequestDto.builder()
                .id("id")
                .userId("user1")
                .title("Watchlist Title 2")
                .description("Watchlist Description 2")
                .build();
        Watchlist originalWatchlist = Watchlist.builder()
                .id("id")
                .userId("user1")
                .title("Watchlist Title")
                .description("Watchlist Description")
                .movieIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();

        when(watchlistRepository.findById(watchlistUpdateRequestDto.getId())).thenReturn(Optional.of(originalWatchlist));

        // Act
        watchlistService.update(watchlistUpdateRequestDto);

        // Assert
        verify(watchlistRepository, times(1)).findById(eq(originalWatchlist.getId()));
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));

        ArgumentCaptor<Watchlist> watchlistArgumentCaptor = ArgumentCaptor.forClass(Watchlist.class);
        verify(watchlistRepository).save(watchlistArgumentCaptor.capture());
        Watchlist capturedWatchlist = watchlistArgumentCaptor.getValue();
        assertEquals(originalWatchlist.getId(), capturedWatchlist.getId());
        assertEquals(watchlistUpdateRequestDto.getTitle(), capturedWatchlist.getTitle());
        assertEquals(watchlistUpdateRequestDto.getDescription(), capturedWatchlist.getDescription());
    }

    // Test for adding a movie to a watchlist
    @Test
    public void testAddMovie() {
        // Arrange
        Watchlist watchlist = Watchlist.builder()
                .id("id")
                .title("test title watchlist")
                .description("test description")
                .movieIds(new HashSet<>(Arrays.asList("123")))
                .userId("123")
                .build();
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
        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title(watchlist.getTitle())
                .description(watchlist.getDescription())
                .userId(watchlist.getUserId())
                .movies(Set.of(movieResponseDto))
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findById(anyString())).thenReturn(Optional.of(watchlist));
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(watchlistResponseDto);
        when(movieClient.findById(anyString(), eq(movie.getImdbID()))).thenReturn(movie);

        // Act
        WatchlistResponseDto result = watchlistService.addMovie(watchlist.getId(), movie.getImdbID());

        // Assert
        assertEquals(WatchlistResponseDto.class, result.getClass());
        verify(watchlistRepository, times(1)).findById(anyString());
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));
        ArgumentCaptor<Watchlist> watchlistArgumentCaptor = ArgumentCaptor.forClass(Watchlist.class);
        verify(watchlistRepository).save(watchlistArgumentCaptor.capture());
        Watchlist capturedWatchlist = watchlistArgumentCaptor.getValue();
        assertEquals(watchlist.getId(), capturedWatchlist.getId());
        assertEquals(watchlist.getTitle(), capturedWatchlist.getTitle());
        assertEquals(watchlist.getDescription(), capturedWatchlist.getDescription());
        assertEquals(watchlist.getMovieIds(), capturedWatchlist.getMovieIds());
    }

    // Test for removing a movie from a watchlist
    @Test
    public void testRemoveMovie() {
        // Arrange
        Watchlist watchlist = Watchlist.builder()
                .id("id")
                .title("test title watchlist")
                .description("test description")
                .movieIds(new HashSet<>(Arrays.asList("123")))
                .userId("123")
                .build();
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
        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title(watchlist.getTitle())
                .description(watchlist.getDescription())
                .userId(watchlist.getUserId())
                .movies(Set.of(movieResponseDto))
                .build();

        when(watchlistRepository.findById(anyString())).thenReturn(Optional.of(watchlist));
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(watchlistResponseDto);

        // Act
        WatchlistResponseDto result = watchlistService.removeMovie(watchlist.getId(), movie.getImdbID());

        // Assert
        assertEquals(WatchlistResponseDto.class, result.getClass());
        verify(watchlistRepository, times(1)).findById(anyString());
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));

        ArgumentCaptor<Watchlist> watchlistArgumentCaptor = ArgumentCaptor.forClass(Watchlist.class);
        verify(watchlistRepository).save(watchlistArgumentCaptor.capture());
        Watchlist capturedWatchlist = watchlistArgumentCaptor.getValue();
        assertEquals(watchlist.getId(), capturedWatchlist.getId());
        assertEquals(watchlist.getTitle(), capturedWatchlist.getTitle());
        assertEquals(watchlist.getDescription(), capturedWatchlist.getDescription());
        assertEquals(watchlist.getMovieIds(), capturedWatchlist.getMovieIds());
    }

    // Test for deleting a watchlist by ID
    @Test
    public void testDeleteById() {
        // Arrange
        String id = "id";

        // Act
        watchlistService.deleteById(id);

        // Assert
        verify(watchlistRepository, times(1)).deleteById(anyString());

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        verify(watchlistRepository).deleteById(idCaptor.capture());
        String capturedId = idCaptor.getValue();
        assertEquals(id, capturedId);
    }

    // Test for finding watchlists by user ID
    @Test
    public void testFindByUserId() {
        // Arrange
        String id = "id";
        List<Watchlist> watchlists = new ArrayList<>();
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
                .imdbID("1")
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
        watchlists.add(Watchlist.builder()
                .userId("1")
                .title("title 1")
                .description("description 1")
                .movieIds(Set.of("1"))
                .build()
        );
        WatchlistResponseDto watchlistResponseDto = WatchlistResponseDto.builder()
                .title("title 1")
                .description("description 1")
                .userId("1")
                .movies(Set.of(movieResponseDto))
                .build();

        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findByUserId(anyString())).thenReturn(watchlists);
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(watchlistResponseDto);
        when(movieClient.findById(anyString(), anyString())).thenReturn(movie);

        // Act
        List<WatchlistResponseDto> results = watchlistService.findByUserId(id);

        // Assert
        assertEquals(watchlists.size(), results.size());
        verify(watchlistRepository, times(1)).findByUserId(anyString());
        verify(watchlistMapper, times(watchlists.size())).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));

        for (int i = 0; i < watchlists.size(); i++) {
            Watchlist originalWatchlist = watchlists.get(i);
            WatchlistResponseDto responseDto = results.get(i);
            assertEquals(originalWatchlist.getUserId(), responseDto.getUserId());
            assertEquals(originalWatchlist.getTitle(), responseDto.getTitle());
            assertEquals(originalWatchlist.getDescription(), responseDto.getDescription());
        }
    }
}