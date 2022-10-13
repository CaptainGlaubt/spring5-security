package se.lernholt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("deprecation")
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsServiceFineGrained() {
        var user1 = User.withUsername("john")
                .password("12345")
                .authorities("READ")
                .build();
        var user2 = User.withUsername("jane")
                .password("12345")
                .authorities("READ", "WRITE", "UPDATE", "DELETE")
                .build();
        var userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
    }

    @Bean
    public UserDetailsService userDetailsServiceCourseGrained() {
        var user1 = User.withUsername("john")
                .password("12345")
                .authorities("ROLE_ADMIN") //Only use prefix ROLE_ when declaring roles as authorities.
                .build();
        var user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER") //Automatically adds ROLE_ prefix.
                .build();
        var userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChainHasAuthority(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .hasAuthority("READ") // User MUST have specific authority.
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainHasRole(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN") // User MUST have specific role.
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainHasAnyAuthority(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .hasAnyAuthority("READ", "WRITE") // User MUST have either authority.
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainHasAnyRole(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .hasAnyRole("ADMIN", "MANAGER") // User MUST have either role.
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainAuthorityAccess(HttpSecurity httpSecurity) throws Exception {
        String expression = "hasAuthority('READ') and !hasAuthority('DELETE')";
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .access(expression) // More complex authority validation.
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainRoleAccess(HttpSecurity httpSecurity) throws Exception {
        String expression = "hasRole('ADMIN') and !hasRole('MANAGER')";
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .access(expression) // More complex authority validation.
                .and()
                .build();
    }
    
    @Bean
    public SecurityFilterChain securityFilterRestrictAll(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .denyAll()
                .and()
                .build();
    }
}
