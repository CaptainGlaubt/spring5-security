package se.lernholt.security.password;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
public final class SpringCrypto {

    private SpringCrypto() {
        // Prevent instantiation.
    }

    @SneakyThrows
    public static void encode() {
        /**
         * PasswordEncoders
         */
        NoOpPasswordEncoder.getInstance();
        new StandardPasswordEncoder();
        new StandardPasswordEncoder("secret");
        new Pbkdf2PasswordEncoder();
        new Pbkdf2PasswordEncoder("secret");
        new Pbkdf2PasswordEncoder("secret", 185000, 256);
        new BCryptPasswordEncoder();
        new BCryptPasswordEncoder(4);
        new BCryptPasswordEncoder(4, SecureRandom.getInstanceStrong());
        new SCryptPasswordEncoder();
        new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        new DelegatingPasswordEncoder("bcrypt", encoders);

        /**
         * Salt generators
         */
        KeyGenerators.string().generateKey();
        KeyGenerators.secureRandom().generateKey();
        KeyGenerators.secureRandom(16).generateKey();
        KeyGenerators.shared(16).generateKey();

        /**
         * Encryptors
         */
        Encryptors.standard("password", "salt");
        Encryptors.stronger("password", "salt");
        Encryptors.noOpText();
        Encryptors.text("password", "salt");
    }
}
