package OMDB.Liantis_Pinseel_Benjamin.services;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieListDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.Liantis_Pinseel_Benjamin.mappers.MovieMapper;
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


    public String decryptedApiKey() {
        return encryptionUtils.decrypt(encryptedApiKey); // Decrypting the API key.
    }

    /**
     * Fetches a movie by its ID.
     *
     * @param id         The ID of the movie to be retrieved.
     * @param type       The type of the movie (optional).
     * @param year       The release year of the movie (optional).
     * @param plot       The plot type of the movie (optional).
     * @param returnType The type of data to be returned (optional).
     * @return MovieResponseDto containing the details of the movie.
     * @throws NullPointerException if the API key is not found.
     */
    public MovieResponseDto findById(String id, String type, int year, String plot, String returnType) {
        if (decryptedApiKey() == null) {
            throw new NullPointerException("API key was not found");
        }
        Movie movie = movieClient.findById(decryptedApiKey(), id, type, year, plot, returnType);
        return movieMapper.mapMovieToDetailedMovieResponseDto(movie);
    }

    /**
     * Searches for a movie by its title.
     *
     * @param title      The title of the movie to be searched.
     * @param type       The type of the movie (optional).
     * @param year       The release year of the movie (optional).
     * @param plot       The plot type of the movie (optional).
     * @param returnType The type of data to be returned (optional).
     * @return MovieResponseDto containing the details of the movie.
     * @throws NullPointerException if the API key is not found.
     */
    public MovieResponseDto findByTitle(String title, String type, int year, String plot, String returnType) {
        if (decryptedApiKey() == null) {
            throw new NullPointerException("API key was not found");
        }
        Movie movie = movieClient.findByTitle(decryptedApiKey(), title, type, year, plot, returnType);
        return movieMapper.mapMovieToDetailedMovieResponseDto(movie);
    }

    /**
     * Retrieves all movies with the specified parameters.
     *
     * @param title The title of the movies to be searched.
     * @param type  The type of the movies (optional).
     * @param year  The release year of the movies (optional).
     * @param page  The page number for pagination.
     * @return Set of MovieResponseDto containing details of the movies.
     * @throws NullPointerException if the API key is not found.
     */
    public PageDto<MovieResponseDto> findAll(String title, String type, int year, int page) {
        if (decryptedApiKey() == null) {
            throw new NullPointerException("API key was not found");
        }
        MovieListDto movieListDto = movieClient.findAll(decryptedApiKey(), title, type, year, page);
        Set<MovieResponseDto> responseDtos = movieListDto.getSearch().stream()
                .map(movie -> movieMapper.mapMovieToMovieShortResponseDto(movie))
                .collect(Collectors.toSet());
        return new PageDto<MovieResponseDto>(
                movieListDto.getTotalResults() / 10,
                movieListDto.getTotalResults(),
                responseDtos
        );
    }
}