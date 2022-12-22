package se.lernholt.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import se.lernholt.security.auth.proxy.AuthenticationProxy;
import se.lernholt.security.auth.token.OneTimePasswordAuthentication;

@Component
@RequiredArgsConstructor
public class OneTimePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationProxy authenticationProxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var code = String.valueOf(authentication.getCredentials());
        var result = authenticationProxy.sendOnetimePassword(username, code);
        if (result) {
            return new OneTimePasswordAuthentication(authentication, authentication);
        }
        throw new BadCredentialsException("Bad credentials.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OneTimePasswordAuthentication.class.isAssignableFrom(authentication);
    }

}
