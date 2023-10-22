package OMDB.Liantis_Pinseel_Benjamin.mappers;

import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import org.springframework.stereotype.Component;

@Component
public class WatchlistMapper {

    public Watchlist mapWatchlistCreateDtoToWatchlist(WatchlistCreateDto watchlistCreateDto) {
        return Watchlist.builder()
                .title(watchlistCreateDto.getTitle())
                .description(watchlistCreateDto.getDescription())
                .build();
    }

    public WatchlistResponseDto mapWatchlistToWatchlistResponseDto(Watchlist updatedWatchlist) {
        return WatchlistResponseDto.builder()
                .title(updatedWatchlist.getTitle())
                .description(updatedWatchlist.getDescription())
                .userId(updatedWatchlist.getUserId())
                .build();
    }
}
