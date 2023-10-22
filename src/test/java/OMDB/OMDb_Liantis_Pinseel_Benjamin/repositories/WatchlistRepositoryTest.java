package OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.services.WatchlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchlistRepositoryTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @InjectMocks
    private WatchlistService watchlistService;

    @Test
    public void testFindByUserId() {
        // Arrange
        Watchlist watchlist1 = Watchlist.builder()
                .title("test title 1")
                .description("test description 1")
                .userId("1")
                .build();
        Watchlist watchlist2 = Watchlist.builder()
                .title("test title 2")
                .description("test description 2")
                .userId("1")
                .build();
        List<Watchlist> watchlists = Arrays.asList(watchlist1, watchlist2);
        when(watchlistRepository.findByUserId("1")).thenReturn(watchlists);

        // Act
        List<Watchlist> foundWatchlists = watchlistRepository.findByUserId("1");

        // Assert
        assertEquals(2, foundWatchlists.size(), "The size of the returned list should be 2");
        assertEquals("1", foundWatchlists.get(0).getUserId(), "The user ID of the first element should be 'user1'");
    }
}
