package se.lernholt.security.password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return hashWithSha512(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StringUtils.equals(encode(rawPassword), encodedPassword);
    }

    @SneakyThrows
    private static String hashWithSha512(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        byte[] digestBytes = messageDigest.digest(passwordBytes);
        StringBuilder encodedPassword = new StringBuilder();
        for (int i = 0; i < digestBytes.length; i++) {
            encodedPassword.append(Integer.toHexString(0xFF & digestBytes[i]));
        }
        return encodedPassword.toString();
    }
}
