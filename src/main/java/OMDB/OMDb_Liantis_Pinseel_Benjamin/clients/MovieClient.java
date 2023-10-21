package OMDB.OMDb_Liantis_Pinseel_Benjamin.clients;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-service", url = "${omdb.api.baseurl}")
public interface MovieClient {

//    @GetMapping("/")
//    Optional<MovieDto> findByFilters(
//            @RequestParam(value = "apikey") String apiKey,
//            @RequestParam(value = "i", required = false) String imdbId,
//            @RequestParam(value = "t", required = false) String title,
//            @RequestParam(value = "type", required = false) String type,
//            @RequestParam(value = "y", required = false) String year,
//            @RequestParam(value = "plot", required = false) String plot,
//            @RequestParam(value = "r", required = false) String returnType,
//            @RequestParam(value = "s", required = false) String searchTerm,
//            @RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "callback", required = false) String callback,
//            @RequestParam(value = "v", required = false) String version
//    );

    @GetMapping("/")
    Movie findById(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "i") String id,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "y", required = false) int year,
            @RequestParam(value = "plot", required = false) String plot,
            @RequestParam(value = "r", required = false) String returnType
    );

    @GetMapping("/")
    Movie findById(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "i") String id
    );

    @GetMapping("/")
    Movie findByTitle(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "t") String title,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "y") int year,
            @RequestParam(value = "plot") String plot,
            @RequestParam(value = "r") String returnType
    );

    @GetMapping("/")
    MovieListDto findAll(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "s") String title,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "y") int year,
            @RequestParam(value = "page") int page
    );
}
