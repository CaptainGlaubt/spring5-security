package se.lernholt.config.basic;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;
import se.lernholt.security.auth.provider.basic.ProjectAuthenticationProvider1;

@SuppressWarnings("deprecation")
@Configuration
@RequiredArgsConstructor
public class ProjectConfig4 extends WebSecurityConfigurerAdapter {

    private final ProjectAuthenticationProvider1 authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests().anyRequest().authenticated();
    }
}
