package se.lernholt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.server.csrf.CsrfToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsrfTokenLoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) request;
        var csrfToken = (CsrfToken) httpServletRequest.getAttribute("_csrf");
        log.info("CSRF Token: {}.", csrfToken);
        chain.doFilter(request, response);
    }
}
