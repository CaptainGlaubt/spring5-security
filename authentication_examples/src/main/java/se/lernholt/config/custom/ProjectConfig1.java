package se.lernholt.config.custom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;
import se.lernholt.security.auth.provider.basic.ProjectAuthenticationProvider2;

@SuppressWarnings("deprecation")
@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ProjectConfig1 extends WebSecurityConfigurerAdapter {

    private final ProjectAuthenticationProvider2 authenticationProvider2;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider2);
    }

    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
