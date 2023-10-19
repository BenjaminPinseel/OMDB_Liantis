package OMDB.OMDb_Liantis_Pinseel_Benjamin.Controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.Mapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private WatchlistService watchlistService;
    private Mapper mapper;

    @GetMapping("/{id}")
    public WatchlistResponseDto findById(@RequestParam String id){
        Watchlist watchlist = watchlistService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found."));
        return mapper.MapWatchlistToWatchlistResponseDto(watchlist);
    }
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void post(@RequestBody WatchlistCreateDto watchlistCreateDto){
        watchlistService.save(watchlistCreateDto);
    }
    @PutMapping()
    public WatchlistResponseDto update(@PathVariable String id,WatchlistUpdateRequestDto watchlistUpdateRequestDto)
    {
        Watchlist updatedWatchlist = watchlistService.update(id,watchlistUpdateRequestDto);
        return mapper.mapWatchlistToWatchlistResponseDto(updatedWatchlist);
    }
}
