package OMDB.OMDb_Liantis_Pinseel_Benjamin.Services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.MovieDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.Watchlist;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions.DataValidationException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.Mapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Repositories.WatchlistRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private WatchlistRepository watchlistRepository;
    private Mapper mapper;

    public WatchlistResponseDto findById(String id){
        Optional<Watchlist> optionalWatchlist = Optional.ofNullable(watchlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Watchlist with this ID was not found.")));
        if (optionalWatchlist.isPresent()){
            WatchlistResponseDto watchlistResponseDto = mapper.mapWatchlistToWatchlistResponseDto(optionalWatchlist.get());
            List<MovieDto> movieDtos = optionalWatchlist.get().getMovieIds().stream().map(movieId -> {
                MovieDto movieDto =  new MovieDto();
                movieDto.setImdbID(movieId);
                return movieDto;
            }).collect(Collectors.toList());

            watchlistResponseDto.setMovieDtos(movieDtos);
            return watchlistResponseDto;
        }
        return null;
    }

    public void save(WatchlistCreateDto watchlistCreateDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<WatchlistCreateDto>> violations = validator.validate(watchlistCreateDto);

        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<WatchlistCreateDto> violation : violations) {
            violationMessages.add(violation.getMessage());
        }

        if (!violationMessages.isEmpty()) {
            throw new DataValidationException(violationMessages);
        } else {
            Watchlist watchlist = mapper.mapWatchlistCreateDtoToWatchlist(watchlistCreateDto);
            watchlistRepository.save(watchlist);
        }
    }

    public Watchlist update(String id, WatchlistUpdateRequestDto watchlistUpdateRequestDto) {
        Watchlist watchlist = watchlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with this ID was not found."));
        watchlist.setTitle(watchlistUpdateRequestDto.getTitle());
        watchlist.setDescription(watchlistUpdateRequestDto.getDescription());
        watchlistRepository.save(watchlist);
        return watchlist;
    }
}
