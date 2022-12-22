package se.lernholt.security.auth.proxy;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import se.lernholt.config.ProjectConfig;

@Component
@RequiredArgsConstructor
public class AuthenticationProxy {

    private final ProjectConfig projectConfig;
    private final RestTemplate  restTemplate;

    public void sendAuth(String username, String password) {
        String requestUrl = String.format("%s/users/auth", projectConfig.getAuthorizationServerBaseUrl());
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        restTemplate.postForEntity(requestUrl, user, Void.class);
    }

    public boolean sendOnetimePassword(String username, String code) {
        String requestUrl = String.format("%s/onetimepasswords/check", projectConfig.getAuthorizationServerBaseUrl());
        User user = User.builder()
                .username(username)
                .code(code)
                .build();
        return restTemplate.postForEntity(requestUrl, user, Void.class)
                .getStatusCode()
                .is2xxSuccessful();
    }
}
