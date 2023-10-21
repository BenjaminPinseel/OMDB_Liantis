package OMDB.OMDb_Liantis_Pinseel_Benjamin.controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.services.MovieService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
@Validated
public class MovieController {
    private final MovieService movieService;
    private final UserMapper mapper;
    private final EncryptionUtils encryptionUtils;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;

    @GetMapping("/{id}")
    public MovieDto findById(@PathVariable @NotNull @NotBlank String id) {
        return movieService.findById(id);
    }

}
