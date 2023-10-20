package OMDB.OMDb_Liantis_Pinseel_Benjamin.Clients;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.MovieDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.EncryptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "movie-service", url = "${omdb.api.baseurl}")
@AllArgsConstructor
public class MovieClient {
    private final EncryptionUtils encryptionUtils;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;

    private String decryptedApiKey = encryptionUtils.decrypt(encryptedApiKey);

   // @GetMapping("/")
    //Optional<MovieDto> findByFilters(
    //        @RequestParam("apikey") String decryptedApiKey,
    //        @RequestParam(value = "s", )
   // );
}
