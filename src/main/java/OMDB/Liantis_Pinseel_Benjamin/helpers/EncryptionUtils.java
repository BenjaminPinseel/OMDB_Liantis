package OMDB.Liantis_Pinseel_Benjamin.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    @Value("${api.password}")
    private String password;

    @Value("${api.salt}")
    private String salt;

    public String encrypt(String apiKey) {
        TextEncryptor encryptor = Encryptors.text(password, salt);
        return encryptor.encrypt(apiKey);
    }

    public String decrypt(String encryptedApiKey) {
        TextEncryptor decryptor = Encryptors.text(password, salt);
        return decryptor.decrypt(encryptedApiKey);
    }
}

