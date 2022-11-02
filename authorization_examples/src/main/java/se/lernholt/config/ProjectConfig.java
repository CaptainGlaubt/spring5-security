package se.lernholt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import se.lernholt.filter.AuthenticationLoggingFilter;
import se.lernholt.filter.RequestValidationFilter;

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
                .authorities("ROLE_ADMIN") // Only use prefix ROLE_ when declaring roles as authorities.
                .build();
        var user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER") // Automatically adds ROLE_ prefix.
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

    @Bean
    public SecurityFilterChain securityFilterMvcMatcher1(HttpSecurity httpSecurity) throws Exception {
        // Use specific matchers before general ones like anyRequest.
        return httpSecurity.httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers("/hello")
                .hasRole("ADMIN")
                .mvcMatchers("/ciao")
                .hasRole("MANAGER")
                .anyRequest()
                .permitAll() // Doesn't actually do anything, but recommended for readability.
//                .authenticated() //Must be authenticated.
//                .denyAll() // If not authorized forbid.
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterMvcMatcher2(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf()
                .disable() // Only disable for test purposes.
                .httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/a")
                .authenticated()
                .mvcMatchers(HttpMethod.POST, "/a")
                .permitAll()
                .anyRequest()
                .denyAll()
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterMvcMatcher3(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers("/a/b/**")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterMvcMatcher4(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers("/product/{code:^[0-9]*$")
                .permitAll()
                .anyRequest()
                .denyAll()
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterAntMatcher1(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/hello") // Does not protect /hello/ whereas doing the same with mvcMatcher would.
                .authenticated()
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterRegexMatcher1(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*")
                .authenticated()
                .anyRequest()
                .hasAuthority("premium")
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain customFilter1(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .build();
    }
}
