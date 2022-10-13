package se.lernholt.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import se.lernholt.domain.security.EncryptionAlgorithm;
import se.lernholt.security.user.JpaUser;
import se.lernholt.security.user.JpaUserDetailsService;

@Component
@RequiredArgsConstructor
public class JpaAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var user = userDetailsService.loadUserByUsername(authentication.getName());
        EncryptionAlgorithm encryptionAlgorithm = ((JpaUser) user).getUser().getEncryptionAlgorithm();
        switch (encryptionAlgorithm) {
        case BCRYPT:
            return completeAuthentication(authentication, user, bCryptPasswordEncoder);
        case SCRYPT:
            return completeAuthentication(authentication, user, sCryptPasswordEncoder);
        default:
            var errorMessage = String.format("Failed to verify user credentials unknown algorithm '%s'.",
                    encryptionAlgorithm);
            throw new IllegalStateException(errorMessage);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private static UsernamePasswordAuthenticationToken completeAuthentication(Authentication authentication,
            UserDetails user, PasswordEncoder passwordEncoder) {
        var password = authentication.getCredentials().toString();
        var encodedPassword = user.getPassword();
        if (passwordEncoder.matches(password, encodedPassword)) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), encodedPassword, user.getAuthorities());
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
