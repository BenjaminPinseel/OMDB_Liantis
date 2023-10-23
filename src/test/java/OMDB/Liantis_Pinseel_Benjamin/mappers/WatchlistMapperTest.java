package OMDB.Liantis_Pinseel_Benjamin.mappers;

import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
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
        assertEquals(watchlistCreateDto.getTitle(), watchlist.getTitle());
        assertEquals(watchlistCreateDto.getDescription(), watchlist.getDescription());
    }

    @Test
    void testMapWatchlistToWatchlistResponseDto() {
        Watchlist watchlist = Watchlist.builder()
                .title("Test Title")
                .description("Test Description")
                .userId("6fa459ea-ee8a-3ca4-894e-db77e160355e")
                .build();

        WatchlistResponseDto watchlistResponseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
        assertEquals(watchlist.getTitle(), watchlistResponseDto.getTitle());
        assertEquals(watchlist.getDescription(), watchlistResponseDto.getDescription());
        assertEquals(watchlist.getUserId(), watchlistResponseDto.getUserId());
    }
}