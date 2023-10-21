package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.clients.MovieClient;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.MovieDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.OmdbFilters;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final EncryptionUtils encryptionUtils;
    private final MovieClient movieClient;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;

    private String decryptedApiKey;

    @PostConstruct
    public void init() {
        decryptedApiKey = EncryptionUtils.decrypt(encryptedApiKey);
    }

    public Optional<MovieDto> findByFilters(OmdbFilters filters) {
        if (decryptedApiKey == null) {
            throw new NullPointerException("API key was not found");
        }
        if (filters.getImdbId() == null && filters.getTitle() == null) {
            throw new IllegalArgumentException("At least one of 'i' or 't' must be provided.");
        }
        return movieClient.findByFilters(decryptedApiKey, filters.getImdbId(), filters.getTitle(), filters.getType(), filters.getYear(),
                filters.getPlot(), filters.getReturnType(), filters.getSearchTerm(), filters.getPage(),
                filters.getCallback(), filters.getVersion());
    }

    public MovieDto findById(String id) {
        if (decryptedApiKey == null) {
            throw new NullPointerException("API key was not found");
        }
        return movieClient.findById(decryptedApiKey, id);
    }
}
