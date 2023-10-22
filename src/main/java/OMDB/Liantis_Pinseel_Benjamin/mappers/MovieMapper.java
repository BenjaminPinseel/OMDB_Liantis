package OMDB.Liantis_Pinseel_Benjamin.mappers;

import OMDB.Liantis_Pinseel_Benjamin.clients.Movie;
import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public PageDto<MovieResponseDto> mapToPageDtoDetailed(Page<Movie> moviesPage) {
        Set<MovieResponseDto> movieResponseDtos = moviesPage.getContent()
                .stream()
                .map(this::mapMovieToDetailedMovieResponseDto)
                .collect(Collectors.toSet());

        return new PageDto<MovieResponseDto>(
                moviesPage.getTotalPages(),
                moviesPage.getTotalElements(),
                movieResponseDtos

        );
    }

    public PageDto<MovieResponseDto> mapToPageDtoShort(Page<Movie> moviesPage) {
        Set<MovieResponseDto> movieResponseDtos = moviesPage.getContent()
                .stream()
                .map(this::mapMovieToMovieShortResponseDto)
                .collect(Collectors.toSet());

        return new PageDto<MovieResponseDto>(
                moviesPage.getTotalPages(),
                moviesPage.getTotalElements(),
                movieResponseDtos

        );
    }

    public MovieResponseDto mapMovieToMovieShortResponseDto(Movie movie) {
        return MovieResponseDto.builder()
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
                .rated(movie.getRated())
                .build();
    }

    public MovieResponseDto mapMovieToDetailedMovieResponseDto(Movie movie) {
        return MovieResponseDto.builder()
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
