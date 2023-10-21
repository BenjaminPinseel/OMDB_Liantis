package OMDB.OMDb_Liantis_Pinseel_Benjamin.controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDetailedResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieShortResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.services.MovieService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
@Validated
public class MovieController {
    private final MovieService movieService;


    @GetMapping("/id/{id}")
    public MovieDetailedResponseDto findById(@PathVariable @NotNull @NotBlank String id,
                                             @RequestParam(required = false) final String type,
                                             @RequestParam(required = false) final int year,
                                             @RequestParam(required = false) final String plot,
                                             @RequestParam(required = false) final String returnType
    ) {
        return movieService.findById(id, type, year, plot, returnType);
    }

    @GetMapping("/title/{title}")
    public MovieDetailedResponseDto findByTitle(@PathVariable final String title,
                                                @RequestParam(required = false) final String type,
                                                @RequestParam(required = false) final int year,
                                                @RequestParam(required = false) final String plot,
                                                @RequestParam(required = false) final String returnType
    ) {
        return movieService.findByTitle(title, type, year, plot, returnType);
    }

    @GetMapping("/")
    public Set<MovieShortResponseDto> findAll(
            @RequestParam() final String title,
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) final int year,
            @RequestParam(required = false, defaultValue = "0") final int page
    ) {
        return movieService.findAll(title, type, year, page);
    }

}
