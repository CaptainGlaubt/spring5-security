package se.lernholt.security.auth.token;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class OneTimePasswordAuthentication extends UsernamePasswordAuthentication {

    private static final long serialVersionUID = 8752769339475919498L;

    public OneTimePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OneTimePasswordAuthentication(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
