package se.lernholt.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import se.lernholt.model.OneTimePassword;
import se.lernholt.model.User;
import se.lernholt.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping("/users/auth")
    public void auth(@RequestBody User user) {
        userService.auth(user);
    }

    @PostMapping("/onetimepasswords/check")
    public void check(@RequestBody OneTimePassword oneTimePassword, HttpServletResponse httpResponse) {
        if (userService.check(oneTimePassword)) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
