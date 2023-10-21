package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.WatchlistMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;

    public WatchlistResponseDto findById(String id) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        if (optionalWatchlist.isPresent()) {
            WatchlistResponseDto watchlistResponseDto = watchlistMapper.mapWatchlistToWatchlistResponseDto(optionalWatchlist.get());
            Set<MovieDto> movieDtos = optionalWatchlist.get().getMovieIds().stream().map(movieId -> {
                MovieDto movieDto = new MovieDto();
                movieDto.setImdbID(movieId);
                return movieDto;
            }).collect(Collectors.toSet());


            watchlistResponseDto.setMovieDtos(movieDtos);
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

    public Watchlist addMovie(String watchlistId, String movieId) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        Watchlist watchlist = optionalWatchlist.get();
        watchlist.getMovieIds().add(movieId);
        watchlistRepository.save(watchlist);
        return watchlist;
    }

    public Watchlist removeMovie(String watchlistId, String movieId) {
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        Watchlist watchlist = optionalWatchlist.get();
        watchlist.getMovieIds().removeIf(id -> id.equals(movieId));
        watchlistRepository.save(watchlist);
        return watchlist;
    }

    public void deleteById(String id) {
        watchlistRepository.deleteById(id);
    }
}
