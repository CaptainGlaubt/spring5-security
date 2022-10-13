package se.lernholt.config.custom;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;
import se.lernholt.security.auth.handler.CustomAuthenticationFailureHandler;
import se.lernholt.security.auth.handler.CustomAuthenticationSuccessHandler;

@SuppressWarnings("deprecation")
@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ProjectConfig3 extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/home", true)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
