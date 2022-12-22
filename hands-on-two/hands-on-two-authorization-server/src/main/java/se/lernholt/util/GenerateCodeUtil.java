package se.lernholt.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {

    private GenerateCodeUtil() {
        // Prevent instantiation.
    }

    public static String generateCode() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            int code = random.nextInt(9000) + 1000;
            return String.valueOf(code);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error ocurred while generating code.", e);
        }
    }
}
