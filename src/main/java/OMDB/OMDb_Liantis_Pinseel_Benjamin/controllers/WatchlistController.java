package OMDB.OMDb_Liantis_Pinseel_Benjamin.controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/watchlist")
@RequiredArgsConstructor
@Validated
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final WatchlistMapper watchlistMapper;

    @GetMapping("/{id}")
    public WatchlistResponseDto findById(@PathVariable String id) {
        return watchlistService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void post(@RequestBody WatchlistCreateDto watchlistCreateDto, @RequestHeader String userId) {
        watchlistService.save(watchlistCreateDto, userId);
    }

    @PutMapping("/update")
    public WatchlistResponseDto update(WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        Watchlist updatedWatchlist = watchlistService.update(watchlistUpdateRequestDto);
        return watchlistMapper.mapWatchlistToWatchlistResponseDto(updatedWatchlist);
    }

    @PostMapping("/{watchlistId}/movie/{movieId}")
    public ResponseEntity<?> addMovie(@PathVariable String watchlistId, @PathVariable String movieId, @RequestHeader String userId) {
        WatchlistResponseDto watchlistData = watchlistService.findById(watchlistId);
        if (watchlistData.getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID does not match the owner of the watchlist");
        } else {
            Watchlist updatedWatchList = watchlistService.addMovie(watchlistId, movieId);
            WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(updatedWatchList);
            return ResponseEntity.ok(responseDto);
        }
    }

    @PutMapping("/{watchlistId}/movie/{movieId}")
    public ResponseEntity<?> removeMovie(@PathVariable String watchlistId, @PathVariable String movieId, @RequestHeader String userId) {
        WatchlistResponseDto watchlistData = watchlistService.findById(watchlistId);
        if (watchlistData.getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID does not match the owner of the watchlist");
        } else {
            Watchlist updatedWatchList = watchlistService.removeMovie(watchlistId, movieId);
            WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(updatedWatchList);
            return ResponseEntity.ok(responseDto);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String id) {
        watchlistService.deleteById(id);
    }
}
