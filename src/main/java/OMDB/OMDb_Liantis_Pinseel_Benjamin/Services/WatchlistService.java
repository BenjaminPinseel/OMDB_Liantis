package OMDB.OMDb_Liantis_Pinseel_Benjamin.Services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.UserCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.WatchlistUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.User;
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

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private WatchlistRepository watchlistRepository;
    private Mapper mapper;

    public Optional<Watchlist> findById(String id){
        return watchlistRepository.findById(id);
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
