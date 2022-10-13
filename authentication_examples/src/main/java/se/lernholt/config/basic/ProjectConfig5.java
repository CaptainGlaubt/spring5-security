package se.lernholt.config.basic;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import se.lernholt.security.user.User2;
import se.lernholt.security.user_details.UserDetailsService1;

@SuppressWarnings("deprecation")
@Configuration
public class ProjectConfig5 {

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetails = new User2("user", "password", "READ");
        return new UserDetailsService1(List.of(userDetails));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
