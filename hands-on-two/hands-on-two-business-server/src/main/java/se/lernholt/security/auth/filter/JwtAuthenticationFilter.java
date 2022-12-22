package se.lernholt.security.auth.filter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.lernholt.security.auth.keystore.AuthenticationKeyStore;
import se.lernholt.security.auth.token.UsernamePasswordAuthentication;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationKeyStore authenticationKeyStore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(jwt)) {
                var signedJwt = SignedJWT.parse(jwt.replace("Bearer ", ""));
                var jwsHeader = signedJwt.getHeader();
                var publicKey = authenticationKeyStore.getKeyPair()
                        .getPublic();
                var jwsVerifier = new DefaultJWSVerifierFactory().createJWSVerifier(jwsHeader, publicKey);
                signedJwt.verify(jwsVerifier);
                var username = signedJwt.getJWTClaimsSet()
                        .getClaim("username");
                if (Objects.nonNull(username)) {
                    var auth = new UsernamePasswordAuthentication(username, signedJwt, List.of(() -> "user"));
                    SecurityContextHolder.getContext()
                            .setAuthentication(auth);
                    doFilter(request, response, filterChain);
                }
                log.warn("Mandatory claim 'username' is missing!");
            }
        } catch (ParseException | JOSEException e) {
            log.warn("Failed to validate JWT.", e);
        }
        throw new BadCredentialsException("Bad credentials!");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath()
                .equals("/login");
    }
}
