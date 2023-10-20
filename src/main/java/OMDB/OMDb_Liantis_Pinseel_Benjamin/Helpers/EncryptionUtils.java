package OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    private static String password;
    private static String salt;

    @Autowired
    public EncryptionUtils(@Value("${api.password}") String password, @Value("${api.salt}") String salt) {
        EncryptionUtils.password = password;
        EncryptionUtils.salt = salt;
    }


    public static String encrypt(String apiKey) {
        TextEncryptor encryptor = Encryptors.text(password, salt);
        return encryptor.encrypt(apiKey);
    }

    public static String decrypt(String encryptedApiKey) {
        TextEncryptor decryptor = Encryptors.text(password, salt);
        return decryptor.decrypt(encryptedApiKey);
    }
}

