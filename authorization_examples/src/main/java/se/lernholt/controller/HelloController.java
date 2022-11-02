package se.lernholt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/hello")
    public String getHello(Authentication authentication) {
        return String.format("Hello '%s' you are now authorized!", authentication.getPrincipal());
    }

    @GetMapping("/ciao")
    public String getCiao(Authentication authentication) {
        return String.format("Ciao '%s' you are now authorized!", authentication.getPrincipal());
    }

    @GetMapping("/hola")
    public String getHola(Authentication authentication) {
        return String.format("Hola '%s' you are now authorized!", authentication.getPrincipal());
    }
}
