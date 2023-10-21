package OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDetailedResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieShortResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public PageDto<MovieDetailedResponseDto> mapToPageDtoDetailed(Page<Movie> moviesPage) {
        Set<MovieDetailedResponseDto> movieResponseDtos = moviesPage.getContent()
                .stream()
                .map(this::mapMovieToMovieDetailedResponseDto)
                .collect(Collectors.toSet());

        return new PageDto<MovieDetailedResponseDto>(
                moviesPage.getTotalPages(),
                moviesPage.getTotalElements(),
                movieResponseDtos

        );
    }

    public PageDto<MovieShortResponseDto> mapToPageDtoShort(Page<Movie> moviesPage) {
        Set<MovieShortResponseDto> movieResponseDtos = moviesPage.getContent()
                .stream()
                .map(this::mapMovieToMovieShortResponseDto)
                .collect(Collectors.toSet());

        return new PageDto<MovieShortResponseDto>(
                moviesPage.getTotalPages(),
                moviesPage.getTotalElements(),
                movieResponseDtos

        );
    }

    public MovieShortResponseDto mapMovieToMovieShortResponseDto(Movie movie) {
        return MovieShortResponseDto.builder()
                .actors(movie.getActors())
                .country(movie.getCountry())
                .director(movie.getDirector())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .ratings(movie.getRatingDtos())
                .Title(movie.getTitle())
                .year(movie.getYear())
                .plot(movie.getPlot())
                .type(movie.getType())
                .build();
    }

    public MovieDetailedResponseDto mapMovieToMovieDetailedResponseDto(Movie movie) {
        return MovieDetailedResponseDto.builder()
                .actors(movie.getActors())
                .country(movie.getCountry())
                .director(movie.getDirector())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .ratings(movie.getRatingDtos())
                .Title(movie.getTitle())
                .year(movie.getYear())
                .type(movie.getType())
                .plot(movie.getPlot())
                .poster(movie.getPoster())
                .rated(movie.getRated())
                .runtime(movie.getRuntime())
                .writer(movie.getWriter())
                .build();
    }

}
