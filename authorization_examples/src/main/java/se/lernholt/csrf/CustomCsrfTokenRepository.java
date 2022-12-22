package se.lernholt.csrf;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final JpaTokenRepository tokenRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String token = UUID.randomUUID()
                .toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        var identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> tokenOpt = tokenRepository.findTokenByIdentifier(identifier);
        if (tokenOpt.isPresent()) {
            Token jpaToken = tokenOpt.get();
            jpaToken.setToken(token.getToken());
        } else {
            Token jpaToken = new Token();
            jpaToken.setToken(token.getToken());
            jpaToken.setIdentifier(identifier);
            tokenRepository.save(jpaToken);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        var identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> tokenOpt = tokenRepository.findTokenByIdentifier(identifier);
        if (tokenOpt.isPresent()) {
            Token token = tokenOpt.get();
            CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
            return csrfToken;
        }
        return null;
    }
}
