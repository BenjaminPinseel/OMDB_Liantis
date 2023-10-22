package OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistMapperTest {

    private final WatchlistMapper watchlistMapper = new WatchlistMapper();
    @Test
    void testMapWatchlistCreateDtoToWatchlist() {
        WatchlistCreateDto watchlistCreateDto = WatchlistCreateDto.builder()
                .title("Test Title")
                .description("Test Description")
                .build();

        Watchlist watchlist = watchlistMapper.mapWatchlistCreateDtoToWatchlist(watchlistCreateDto);
        assertEquals("Test Title", watchlist.getTitle());
        assertEquals("Test Description", watchlist.getDescription());
    }

    @Test
    void testMapWatchlistToWatchlistResponseDto() {
        Watchlist watchlist = Watchlist.builder()
                .title("Test Title")
                .description("Test Description")
                .userId("6fa459ea-ee8a-3ca4-894e-db77e160355e")
                .build();

        WatchlistResponseDto watchlistResponseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
        assertEquals("Test Title", watchlistResponseDto.getTitle());
        assertEquals("Test Description", watchlistResponseDto.getDescription());
        assertEquals("6fa459ea-ee8a-3ca4-894e-db77e160355e", watchlistResponseDto.getUserId());
    }
}