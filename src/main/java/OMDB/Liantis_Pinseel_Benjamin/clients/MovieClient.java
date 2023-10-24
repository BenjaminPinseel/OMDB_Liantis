package OMDB.Liantis_Pinseel_Benjamin.clients;

import OMDB.Liantis_Pinseel_Benjamin.dto.MovieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-service", url = "${omdb.api.baseurl}")
public interface MovieClient {

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
            @RequestParam(value = "y") Integer year,
            @RequestParam(value = "plot") String plot,
            @RequestParam(value = "r") String returnType
    );

    @GetMapping("/")
    MovieListDto findAll(
            @RequestParam(value = "apikey") String apiKey,
            @RequestParam(value = "s") String title,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "y") Integer year,
            @RequestParam(value = "page") int page
    );
}
