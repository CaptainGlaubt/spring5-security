package se.lernholt.security.auth.provider.basic;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ProjectAuthenticationProvider1 implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = String.valueOf(authentication.getCredentials());
        if (StringUtils.equals(username, "sa") && StringUtils.equals(password, "sa")) {
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        }
        throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
