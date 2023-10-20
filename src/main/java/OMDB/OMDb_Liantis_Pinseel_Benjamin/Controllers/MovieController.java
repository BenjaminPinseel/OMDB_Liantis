package OMDB.OMDb_Liantis_Pinseel_Benjamin.Controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.Mapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
public class MovieController {
    private final UserService userService;
    private final Mapper mapper;
    private final EncryptionUtils encryptionUtils;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;


}
