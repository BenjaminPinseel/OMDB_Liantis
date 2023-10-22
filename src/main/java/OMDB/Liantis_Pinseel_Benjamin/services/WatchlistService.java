package OMDB.Liantis_Pinseel_Benjamin.services;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import OMDB.Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.Liantis_Pinseel_Benjamin.repositories.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;
    private final MovieMapper movieMapper;
    private final MovieClient movieClient;
    private final EncryptionUtils encryptionUtils;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;

    public String decryptedApiKey() {
        return encryptionUtils.decrypt(encryptedApiKey); // Decrypting the API key.
    }

    /**
     * Finds a watchlist by its ID.
     *
     * @param id The ID of the watchlist to be retrieved.
     * @return WatchlistResponseDto containing the details of the watchlist.
     * @throws ResourceNotFoundException if the watchlist is not found.
     */
    public WatchlistResponseDto findById(String id) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        if (optionalWatchlist.isPresent()) {
            WatchlistResponseDto watchlistResponseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(optionalWatchlist.get());
            Set<MovieResponseDto> movies = optionalWatchlist.get().getMovieIds().stream().map(movieId -> {
                Movie movie = movieClient.findById(decryptedApiKey(), movieId);
                return movieMapper.mapMovieToMovieShortResponseDto(movie);
            }).collect(Collectors.toSet());


            watchlistResponseDto.setMovies(movies);
            return watchlistResponseDto;
        }
        return null;
    }

    /**
     * Saves a new watchlist.
     *
     * @param watchlistCreateDto WatchlistCreateDto containing the details of the new watchlist.
     * @param userId             The ID of the user associated with the watchlist.
     */
    public void save(WatchlistCreateDto watchlistCreateDto, String userId) {
        Watchlist watchlist = Watchlist.builder()
                .title(watchlistCreateDto.getTitle())
                .description(watchlistCreateDto.getDescription())
                .userId(userId)
                .build();
        watchlistRepository.save(watchlist);

    }

    /**
     * Updates an existing watchlist.
     *
     * @param watchlistUpdateRequestDto WatchlistUpdateRequestDto containing the updated watchlist data.
     * @return Updated Watchlist entity.
     * @throws ResourceNotFoundException if the watchlist is not found.
     */
    public Watchlist update(WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        Watchlist watchlist = watchlistRepository.findById(watchlistUpdateRequestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found."));
        watchlist.setTitle(watchlistUpdateRequestDto.getTitle());
        watchlist.setDescription(watchlistUpdateRequestDto.getDescription());
        watchlistRepository.save(watchlist);
        return watchlist;
    }

    /**
     * Adds a movie to the specified watchlist.
     *
     * @param watchlistId The ID of the watchlist to which the movie is to be added.
     * @param movieId     The ID of the movie to be added.
     * @return WatchlistResponseDto containing the updated watchlist details.
     * @throws ResourceNotFoundException if the watchlist is not found.
     */
    public WatchlistResponseDto addMovie(String watchlistId, String movieId) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        Watchlist watchlist = optionalWatchlist.get();
        watchlist.getMovieIds().add(movieId);
        watchlistRepository.save(watchlist);
        WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
        Set<MovieResponseDto> movies = watchlist.getMovieIds().stream().map(id -> {
            Movie movie = movieClient.findById(decryptedApiKey(), id);
            return movieMapper.mapMovieToMovieShortResponseDto(movie);
        }).collect(Collectors.toSet());
        responseDto.setMovies(movies);
        return responseDto;
    }

    /**
     * Removes a movie from the specified watchlist.
     *
     * @param watchlistId The ID of the watchlist from which the movie is to be removed.
     * @param movieId     The ID of the movie to be removed.
     * @return WatchlistResponseDto containing the updated watchlist details.
     * @throws ResourceNotFoundException if the watchlist is not found.
     */
    public WatchlistResponseDto removeMovie(String watchlistId, String movieId) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        Watchlist watchlist = optionalWatchlist.get();
        watchlist.getMovieIds().removeIf(id -> id.equals(movieId));
        watchlistRepository.save(watchlist);
        WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
        Set<MovieResponseDto> movies = watchlist.getMovieIds().stream().map(id -> {
            Movie movie = movieClient.findById(decryptedApiKey(), id);
            return movieMapper.mapMovieToMovieShortResponseDto(movie);
        }).collect(Collectors.toSet());

        responseDto.setMovies(movies);
        return responseDto;
    }

    /**
     * Deletes a watchlist by its ID.
     *
     * @param id The ID of the watchlist to be deleted.
     */
    public void deleteById(String id) {
        watchlistRepository.deleteById(id);
    }

    /**
     * Finds all watchlists associated with a specific user.
     *
     * @param id The ID of the user whose watchlists are to be retrieved.
     * @return List of WatchlistResponseDto containing the details of the user's watchlists.
     */
    public List<WatchlistResponseDto> findByUserId(String id) {
        List<Watchlist> watchlists = watchlistRepository.findByUserId(id);
        ArrayList<WatchlistResponseDto> responseWatchlists = new ArrayList<WatchlistResponseDto>() {
        };
        for (Watchlist watchlist : watchlists) {
            WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
            Set<MovieResponseDto> movies = watchlist.getMovieIds().stream().map(movieId -> {
                Movie movie = movieClient.findById(decryptedApiKey(), movieId);
                return movieMapper.mapMovieToMovieShortResponseDto(movie);
            }).collect(Collectors.toSet());

            responseDto.setMovies(movies);
            responseWatchlists.add(responseDto);
        }
        return responseWatchlists;
    }
}
