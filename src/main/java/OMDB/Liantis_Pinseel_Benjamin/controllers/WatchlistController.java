package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.Liantis_Pinseel_Benjamin.services.WatchlistService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/watchlist")
@RequiredArgsConstructor
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final WatchlistMapper watchlistMapper;

    @GetMapping("/user/{id}")
    public List<WatchlistResponseDto> findByUserId(@PathVariable String id) {
        return watchlistService.findByUserId(id);
    }

    @GetMapping("/{id}")
    public WatchlistResponseDto findById(@PathVariable String id) {
        return watchlistService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void post(@RequestBody @Valid WatchlistCreateDto watchlistCreateDto, @RequestHeader String userId) {
        watchlistService.save(watchlistCreateDto, userId);
    }

    @PutMapping("/update")
    public WatchlistResponseDto update(@Valid WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        Watchlist updatedWatchlist = watchlistService.update(watchlistUpdateRequestDto);
        return watchlistMapper.mapWatchlistToWatchlistResponseDto(updatedWatchlist);
    }

    @PostMapping("/{watchlistId}/movie/{movieId}")
    public ResponseEntity<?> addMovie(@PathVariable String watchlistId, @PathVariable String movieId, @RequestHeader @NotBlank String userId) {
        WatchlistResponseDto watchlistData = watchlistService.findById(watchlistId);
        if (!StringUtils.equals(watchlistData.getUserId(), userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID does not match the owner of the watchlist");
        } else {
            WatchlistResponseDto updatedWatchListResponseDto = watchlistService.addMovie(watchlistId, movieId);
            return ResponseEntity.ok(updatedWatchListResponseDto);
        }
    }

    @PutMapping("/{watchlistId}/movie/{movieId}")
    public ResponseEntity<?> removeMovie(@PathVariable String watchlistId, @PathVariable String movieId, @RequestHeader String userId) {
        WatchlistResponseDto watchlistData = watchlistService.findById(watchlistId);
        if (!StringUtils.equals(watchlistData.getUserId(), userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID does not match the owner of the watchlist");
        } else {
            WatchlistResponseDto updatedWatchListResponseDto = watchlistService.removeMovie(watchlistId, movieId);
            return ResponseEntity.ok(updatedWatchListResponseDto);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String id) {
        watchlistService.deleteById(id);
    }
}
