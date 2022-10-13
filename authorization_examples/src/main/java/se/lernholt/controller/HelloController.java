package se.lernholt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/hello")
    public String main(Authentication authentication) {
        return String.format("Hello '%s' you are now authorized!", authentication.getPrincipal());
    }
}
