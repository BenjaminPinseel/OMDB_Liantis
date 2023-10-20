package OMDB.OMDb_Liantis_Pinseel_Benjamin.Controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistMovieRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.Mapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private WatchlistService watchlistService;
    private Mapper mapper;

    @GetMapping("/{id}")
    public WatchlistResponseDto findById(@RequestParam String id) {
        return watchlistService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void post(@RequestBody WatchlistCreateDto watchlistCreateDto) {
        watchlistService.save(watchlistCreateDto);
    }

    @PutMapping("/update")
    public WatchlistResponseDto update(WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        Watchlist updatedWatchlist = watchlistService.update(watchlistUpdateRequestDto);
        return mapper.mapWatchlistToWatchlistResponseDto(updatedWatchlist);
    }
    @PutMapping("/addmovie")
    public ResponseEntity<?> addMovieToWatchlist(@RequestBody WatchlistMovieRequestDto watchlistMovieRequestDto)
    {
        Watchlist updatedWatchList = watchlistService.addMovie(watchlistMovieRequestDto);
        if (updatedWatchList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID does not match the owner of the watchlist");
        }
        else{
            WatchlistResponseDto responseDto = mapper.mapWatchlistToWatchlistResponseDto(updatedWatchList);
            return ResponseEntity.ok(responseDto);
        }
    }
    @PutMapping("/removemovie")
    public ResponseEntity<?> removeMovieToWatchlist(@RequestBody WatchlistMovieRequestDto watchlistMovieRequestDto)
    {
        Watchlist updatedWatchList = watchlistService.removeMovie(watchlistMovieRequestDto);
        if (updatedWatchList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID does not match the owner of the watchlist");
        }
        else{
            WatchlistResponseDto responseDto = mapper.mapWatchlistToWatchlistResponseDto(updatedWatchList);
            return ResponseEntity.ok(responseDto);
        }

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam String id){
        watchlistService.deleteById(id);
    }
}
