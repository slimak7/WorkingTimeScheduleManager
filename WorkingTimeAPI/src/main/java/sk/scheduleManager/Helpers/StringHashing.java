package sk.scheduleManager.Helpers;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class StringHashing {

    public static String GetPBKDF2(String text) throws NoSuchAlgorithmException, InvalidKeySpecException {

        var saltKey = "ggbJJkon252_kllp";
        byte[] salt = saltKey.getBytes();

        KeySpec spec = new PBEKeySpec(text.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return new String(hash, StandardCharsets.UTF_8);
    }
}
