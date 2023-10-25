package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.dto.MovieResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.services.MovieService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieController {
    private final MovieService movieService;

    /**
     * Retrieve a movie by its unique identifier.
     *
     * @param id The unique identifier of the movie.
     * @return MovieResponseDto containing information about the movie.
     */
    @GetMapping("/id/{id}")
    public MovieResponseDto findById(@PathVariable @NotNull @NotBlank String id
    ) {
        return movieService.findById(id);
    }

    /**
     * Retrieve a movie by its title with optional parameters.
     *
     * @param title      The title of the movie.
     * @param type       Optional type of the movie.
     * @param year       Optional year of the movie.
     * @param plot       Optional plot details of the movie.
     * @param returnType Optional return type of the response.
     * @return MovieResponseDto containing information about the movie.
     */
    @GetMapping("/title/{title}")
    public MovieResponseDto findByTitle(@PathVariable final String title,
                                        @RequestParam(required = false) final String type,
                                        @RequestParam(required = false) final Integer year,
                                        @RequestParam(required = false) final String plot,
                                        @RequestParam(required = false) final String returnType
    ) {
        return movieService.findByTitle(title, type, year, plot, returnType);
    }

    /**
     * Retrieve a list of movies based on specified parameters.
     *
     * @param title The title of the movie.
     * @param type  Optional type of the movie.
     * @param year  Optional year of the movie.
     * @param page  Optional page number for pagination (default value: 1).
     * @return PageDto<MovieResponseDto> containing a list of movies.
     */
    @GetMapping({"/", ""})
    public PageDto<MovieResponseDto> findAll(
            @RequestParam() final String title,
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) final Integer year,
            @RequestParam(required = false, defaultValue = "1") final int page
    ) {
        return movieService.findAll(title, type, year, page);
    }

}
