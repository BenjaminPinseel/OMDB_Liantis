package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDetailedResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieListDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieShortResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.MovieMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final EncryptionUtils encryptionUtils;
    private final MovieClient movieClient;
    private final MovieMapper movieMapper;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;

    private String decryptedApiKey;

    @PostConstruct
    public void init() {
        decryptedApiKey = EncryptionUtils.decrypt(encryptedApiKey);
    }

//    public Optional<MovieDto> findByFilters(OmdbFilters filters) {
//        if (decryptedApiKey == null) {
//            throw new NullPointerException("API key was not found");
//        }
//        if (filters.getImdbId() == null && filters.getTitle() == null) {
//            throw new IllegalArgumentException("At least one of 'i' or 't' must be provided.");
//        }
//        return movieClient.findByFilters(decryptedApiKey, filters.getImdbId(), filters.getTitle(), filters.getType(), filters.getYear(),
//                filters.getPlot(), filters.getReturnType(), filters.getSearchTerm(), filters.getPage(),
//                filters.getCallback(), filters.getVersion());
//    }

    public MovieDetailedResponseDto findById(String id, String type, int year, String plot, String returnType) {
        if (decryptedApiKey == null) {
            throw new NullPointerException("API key was not found");
        }
        Movie movie = movieClient.findById(decryptedApiKey, id, type, year, plot, returnType);
        return movieMapper.mapMovieToMovieDetailedResponseDto(movie);
    }

    public MovieDetailedResponseDto findByTitle(String title, String type, int year, String plot, String returnType) {
        if (decryptedApiKey == null) {
            throw new NullPointerException("API key was not found");
        }
        Movie movie = movieClient.findByTitle(decryptedApiKey, title, type, year, plot, returnType);
        return movieMapper.mapMovieToMovieDetailedResponseDto(movie);
    }

    public Set<MovieShortResponseDto> findAll(String title, String type, int year, int page) {
        if (decryptedApiKey == null) {
            throw new NullPointerException("API key was not found");
        }
        MovieListDto movieListDto = movieClient.findAll(decryptedApiKey, title, type, year, page);
        Set<MovieShortResponseDto> responseDtos = movieListDto.getSearch().stream().map(movie -> {
            return movieMapper.mapMovieToMovieShortResponseDto(movie);
        }).collect(Collectors.toSet());
        return responseDtos;
    }
}
