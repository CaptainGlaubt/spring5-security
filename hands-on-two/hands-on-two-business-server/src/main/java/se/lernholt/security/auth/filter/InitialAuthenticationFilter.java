package se.lernholt.security.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import se.lernholt.security.auth.keystore.AuthenticationKeyStore;
import se.lernholt.security.auth.token.OneTimePasswordAuthentication;

@Component
@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager  authenticationManager;
    private final AuthenticationKeyStore authenticationKeyStore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var code = request.getHeader("code");
        if (StringUtils.isBlank(code)) {
            var auth = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(auth);
        } else {
            Authentication auth = new OneTimePasswordAuthentication(username, code);
            auth = authenticationManager.authenticate(auth);
            var header = new JWSHeader(JWSAlgorithm.RS256);
            var claimsSet = new JWTClaimsSet.Builder().claim("username", username)
                    .build();
            var jwt = new SignedJWT(header, claimsSet);
            var privateKey = authenticationKeyStore.getKeyPair()
                    .getPrivate();
            var signer = new RSASSASigner(privateKey);
            try {
                jwt.sign(signer);
            } catch (JOSEException e) {
            }
            response.setHeader("Authorization", jwt.serialize());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath()
                .equals("/login");
    }
}
