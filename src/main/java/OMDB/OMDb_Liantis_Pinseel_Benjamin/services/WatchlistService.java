package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieShortResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories.WatchlistRepository;
import jakarta.annotation.PostConstruct;
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

    private String decryptedApiKey;

    @PostConstruct
    public void init() {
        decryptedApiKey = EncryptionUtils.decrypt(encryptedApiKey);
    }

    public WatchlistResponseDto findById(String id) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        if (optionalWatchlist.isPresent()) {
            WatchlistResponseDto watchlistResponseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(optionalWatchlist.get());
            Set<MovieShortResponseDto> movies = optionalWatchlist.get().getMovieIds().stream().map(movieId -> {
                Movie movie = movieClient.findById(decryptedApiKey, movieId);
                return movieMapper.mapMovieToMovieShortResponseDto(movie);
            }).collect(Collectors.toSet());


            watchlistResponseDto.setMovies(movies);
            return watchlistResponseDto;
        }
        return null;
    }

    public void save(WatchlistCreateDto watchlistCreateDto, String userId) {
        Watchlist watchlist = Watchlist.builder()
                .title(watchlistCreateDto.getTitle())
                .description(watchlistCreateDto.getDescription())
                .userId(userId)
                .build();
        watchlistRepository.save(watchlist);

    }

    public Watchlist update(WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        Watchlist watchlist = watchlistRepository.findById(watchlistUpdateRequestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found."));
        watchlist.setTitle(watchlistUpdateRequestDto.getTitle());
        watchlist.setDescription(watchlistUpdateRequestDto.getDescription());
        watchlistRepository.save(watchlist);
        return watchlist;
    }

    public WatchlistResponseDto addMovie(String watchlistId, String movieId) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        Watchlist watchlist = optionalWatchlist.get();
        watchlist.getMovieIds().add(movieId);
        watchlistRepository.save(watchlist);
        WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
        Set<MovieShortResponseDto> movies = watchlist.getMovieIds().stream().map(id -> {
            Movie movie = movieClient.findById(decryptedApiKey, id);
            return movieMapper.mapMovieToMovieShortResponseDto(movie);
        }).collect(Collectors.toSet());
        responseDto.setMovies(movies);
        return responseDto;
    }

    public WatchlistResponseDto removeMovie(String watchlistId, String movieId) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        Watchlist watchlist = optionalWatchlist.get();
        watchlist.getMovieIds().removeIf(id -> id.equals(movieId));
        watchlistRepository.save(watchlist);
        WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
        Set<MovieShortResponseDto> movies = watchlist.getMovieIds().stream().map(id -> {
            Movie movie = movieClient.findById(decryptedApiKey, id);
            return movieMapper.mapMovieToMovieShortResponseDto(movie);
        }).collect(Collectors.toSet());

        responseDto.setMovies(movies);
        return responseDto;
    }

    public void deleteById(String id) {
        watchlistRepository.deleteById(id);
    }

    public List<WatchlistResponseDto> findByUserId(String id) {
        List<Watchlist> watchlists = watchlistRepository.findByUserId(id);
        ArrayList<WatchlistResponseDto> responseWatchlists = new ArrayList<WatchlistResponseDto>() {
        };
        for (Watchlist watchlist : watchlists) {
            WatchlistResponseDto responseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(watchlist);
            Set<MovieShortResponseDto> movies = watchlist.getMovieIds().stream().map(movieId -> {
                Movie movie = movieClient.findById(decryptedApiKey, movieId);
                return movieMapper.mapMovieToMovieShortResponseDto(movie);
            }).collect(Collectors.toSet());

            responseDto.setMovies(movies);
            responseWatchlists.add(responseDto);
        }
        return responseWatchlists;
    }
}
