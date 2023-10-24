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


    @GetMapping("/id/{id}")
    public MovieResponseDto findById(@PathVariable @NotNull @NotBlank String id
    ) {
        return movieService.findById(id);
    }

    @GetMapping("/title/{title}")
    public MovieResponseDto findByTitle(@PathVariable final String title,
                                        @RequestParam(required = false) final String type,
                                        @RequestParam(required = false) final Integer year,
                                        @RequestParam(required = false) final String plot,
                                        @RequestParam(required = false) final String returnType
    ) {
        return movieService.findByTitle(title, type, year, plot, returnType);
    }

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
