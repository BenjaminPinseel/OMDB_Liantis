package OMDB.OMDb_Liantis_Pinseel_Benjamin.clients;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.OmdbFilters;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "movie-service", url = "${omdb.api.baseurl}")
public interface MovieClient {

    @GetMapping("/")
    Optional<MovieDto> findByFilters(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "i", required = false) String imdbId,
            @RequestParam(value = "t", required = false) String title,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "y", required = false) String year,
            @RequestParam(value = "plot", required = false) String plot,
            @RequestParam(value = "r", required = false) String returnType,
            @RequestParam(value = "s", required = false) String searchTerm,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "callback", required = false) String callback,
            @RequestParam(value = "v", required = false) String version
    );

    @GetMapping("/")
    MovieDto findById(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "i") String id
    );
}
