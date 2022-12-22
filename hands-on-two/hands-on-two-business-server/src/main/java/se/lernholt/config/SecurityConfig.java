package se.lernholt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import se.lernholt.security.auth.filter.InitialAuthenticationFilter;
import se.lernholt.security.auth.filter.JwtAuthenticationFilter;
import se.lernholt.security.auth.provider.OneTimePasswordAuthenticationProvider;
import se.lernholt.security.auth.provider.UsernamePasswordAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OneTimePasswordAuthenticationProvider  oneTimePasswordAuthenticationProvider;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(oneTimePasswordAuthenticationProvider)
                .authenticationProvider(usernamePasswordAuthenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
            InitialAuthenticationFilter initialAuthenticationFilter, JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        return httpSecurity.csrf()
                .disable()
                .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }
}
