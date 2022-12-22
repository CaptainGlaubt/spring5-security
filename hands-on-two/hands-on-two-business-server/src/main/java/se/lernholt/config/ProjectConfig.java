package se.lernholt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "lernholt")
@Data
public class ProjectConfig {

    private String authorizationServerBaseUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
