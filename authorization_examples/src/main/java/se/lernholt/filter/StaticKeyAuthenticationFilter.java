package se.lernholt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class StaticKeyAuthenticationFilter implements Filter {

    @Value("${authorization.key}")
    private String authorizationKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        String authentication = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.equals(authorizationKey, authentication)) {
            doFilter(request, response, chain);
        } else {
            var httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
