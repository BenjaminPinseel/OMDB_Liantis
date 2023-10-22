package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories.WatchlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private WatchlistRepository watchlistRepository;

    @Mock
    private WatchlistMapper watchlistMapper;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private MovieClient movieClient;

    @Mock
    private EncryptionUtils encryptionUtils;

    @InjectMocks
    private WatchlistService watchlistService;

    // Test for finding an existing watchlist by ID
    @Test
    public void testFindById_ExistingWatchlist_ReturnsWatchlistResponseDto() {
        // Arrange
        Watchlist watchlist = Watchlist.builder()
                .id("1")
                .title("test title watchlist")
                .description("test description")
                .movieIds(Set.of("123"))
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findById(anyString())).thenReturn(Optional.of(watchlist));
        when(movieClient.findById(anyString(), anyString())).thenReturn(Movie.builder()
                .title("test title")
                .type("movie")
                .plot("test plot")
                .actors("henk")
                .country("USA")
                .language("English")
                .poster("")
                .year("2001")
                .imdbID("123")
                .build()
        );
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(new WatchlistResponseDto());

        // Act
        WatchlistResponseDto result = watchlistService.findById("id");

        // Assert
        assertEquals(WatchlistResponseDto.class, result.getClass());
        verify(watchlistRepository, times(1)).findById(anyString());
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));
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

        // Act
        watchlistService.save(watchlistCreateDto, userId);

        // Assert
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
    }

    // Test for updating a watchlist
    @Test
    public void testUpdate() {
        // Arrange
        WatchlistUpdateRequestDto watchlistUpdateRequestDto = new WatchlistUpdateRequestDto();
        watchlistUpdateRequestDto.setId("id");
        when(watchlistRepository.findById(anyString())).thenReturn(Optional.of(new Watchlist()));

        // Act
        watchlistService.update(watchlistUpdateRequestDto);

        // Assert
        verify(watchlistRepository, times(1)).findById(anyString());
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
    }

    // Test for adding a movie to a watchlist
    @Test
    public void testAddMovie() {
        // Arrange
        String watchlistId = "watchlistId";
        String movieId = "movieId";
        HashSet<String> movieIds = new HashSet<>();
        movieIds.add("1");
        Watchlist watchlist = Watchlist.builder()
                .id("1")
                .title("title 1")
                .description("description 1")
                .movieIds(movieIds)
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findById(anyString())).thenReturn(Optional.of(watchlist));
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(new WatchlistResponseDto());
        when(movieClient.findById(anyString(), anyString())).thenReturn(new Movie());

        // Act
        WatchlistResponseDto result = watchlistService.addMovie(watchlistId, movieId);

        // Assert
        assertEquals(WatchlistResponseDto.class, result.getClass());
        verify(watchlistRepository, times(1)).findById(anyString());
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));
    }

    // Test for removing a movie from a watchlist
    @Test
    public void testRemoveMovie() {
        // Arrange
        String watchlistId = "watchlistId";
        String movieId = "movieId";
        HashSet<String> movieIds = new HashSet<>();
        movieIds.add("1");
        Watchlist watchlist = Watchlist.builder()
                .id("1")
                .title("title 1")
                .description("description 1")
                .movieIds(movieIds)
                .build();
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findById(anyString())).thenReturn(Optional.of(watchlist));
        when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(new WatchlistResponseDto());
        when(movieClient.findById(anyString(), anyString())).thenReturn(new Movie());

        // Act
        WatchlistResponseDto result = watchlistService.removeMovie(watchlistId, movieId);

        // Assert
        assertEquals(WatchlistResponseDto.class, result.getClass());
        verify(watchlistRepository, times(1)).findById(anyString());
        verify(watchlistRepository, times(1)).save(any(Watchlist.class));
        verify(watchlistMapper, times(1)).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));
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
    }

    // Test for finding watchlists by user ID
    @Test
    public void testFindByUserId() {
        // Arrange
        String id = "id";
        List<Watchlist> watchlists = new ArrayList<>();
        watchlists.add(Watchlist.builder()
                .userId("1")
                .title("title 1")
                .description("description 1")
                .movieIds(Set.of("1"))
                .build()
        );
        watchlists.add(Watchlist.builder()
                .userId("1")
                .title("title 2")
                .description("description 2")
                .movieIds(Set.of("2"))
                .build()
        );
        when(encryptionUtils.decrypt(any())).thenReturn("1");
        when(watchlistRepository.findByUserId(anyString())).thenReturn(watchlists);
       when(watchlistMapper.mapWatchlistToWatchlistResponseDto(any(Watchlist.class))).thenReturn(new WatchlistResponseDto());
        when(movieClient.findById(anyString(), anyString())).thenReturn(new Movie());

        // Act
        List<WatchlistResponseDto> results = watchlistService.findByUserId(id);

        // Assert
        assertEquals(watchlists.size(), results.size());
        verify(watchlistRepository, times(1)).findByUserId(anyString());
        verify(watchlistMapper, times(watchlists.size())).mapWatchlistToWatchlistResponseDto(any(Watchlist.class));
    }
}