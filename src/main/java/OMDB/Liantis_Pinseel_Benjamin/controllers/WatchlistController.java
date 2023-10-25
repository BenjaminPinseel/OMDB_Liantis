package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
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

    /**
     * Retrieve a list of watchlists by the user's identifier.
     *
     * @param id The unique identifier of the user.
     * @return List<WatchlistResponseDto> containing watchlists associated with the user.
     */
    @GetMapping("/user/{id}")
    public List<WatchlistResponseDto> findByUserId(@PathVariable String id) {
        return watchlistService.findByUserId(id);
    }

    /**
     * Retrieve a watchlist by its unique identifier.
     *
     * @param id The unique identifier of the watchlist.
     * @return WatchlistResponseDto containing information about the watchlist.
     */
    @GetMapping("/{id}")
    public WatchlistResponseDto findById(@PathVariable String id) {
        return watchlistService.findById(id);
    }

    /**
     * Create a new watchlist.
     *
     * @param watchlistCreateDto The WatchlistCreateDto containing information for the new watchlist.
     * @param userId             The identifier of the user creating the watchlist.
     */
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody @Valid WatchlistCreateDto watchlistCreateDto, @RequestHeader String userId) {
        watchlistService.save(watchlistCreateDto, userId);
    }

    /**
     * Update an existing watchlist.
     *
     * @param watchlistUpdateRequestDto The WatchlistUpdateRequestDto containing updated information for the watchlist.
     * @return ResponseEntity containing information about the updated watchlist or an error message if the watchlist is not found or the user ID does not match the owner of the watchlist.
     */
    @PutMapping({"/", ""})
    public ResponseEntity<?> update(@RequestBody @Valid WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        WatchlistResponseDto watchlist = watchlistService.findById(watchlistUpdateRequestDto.getId());
        if (watchlist == null) {
            throw new ResourceNotFoundException("Watchlist not found");
        }

        if (!StringUtils.equalsIgnoreCase(watchlist.getUserId(), watchlistUpdateRequestDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID does not match the owner of the watchlist");
        }
        Watchlist updatedWatchlist = watchlistService.update(watchlistUpdateRequestDto);
        return ResponseEntity.ok().body(watchlistMapper.mapWatchlistToWatchlistResponseDto(updatedWatchlist));
    }

    /**
     * Add a movie to a specific watchlist.
     *
     * @param watchlistId The unique identifier of the watchlist.
     * @param movieId     The unique identifier of the movie to be added.
     * @param userId      The identifier of the user performing the action.
     * @return ResponseEntity containing information about the updated watchlist or an error message if the user ID does not match the owner of the watchlist.
     */
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

    /**
     * Remove a movie from a specific watchlist.
     *
     * @param watchlistId The unique identifier of the watchlist.
     * @param movieId     The unique identifier of the movie to be removed.
     * @param userId      The identifier of the user performing the action.
     * @return ResponseEntity containing information about the updated watchlist or an error message if the user ID does not match the owner of the watchlist.
     */
    @DeleteMapping("/{watchlistId}/movie/{movieId}")
    public ResponseEntity<?> removeMovie(@PathVariable String watchlistId, @PathVariable String movieId, @RequestHeader String userId) {
        WatchlistResponseDto watchlistData = watchlistService.findById(watchlistId);
        if (!StringUtils.equals(watchlistData.getUserId(), userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID does not match the owner of the watchlist");
        } else {
            WatchlistResponseDto updatedWatchListResponseDto = watchlistService.removeMovie(watchlistId, movieId);
            return ResponseEntity.ok(updatedWatchListResponseDto);
        }

    }

    /**
     * Delete a watchlist by its unique identifier.
     *
     * @param id The unique identifier of the watchlist to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String id) {
        watchlistService.deleteById(id);
    }
}
