package se.lernholt.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import se.lernholt.model.OneTimePassword;
import se.lernholt.model.User;
import se.lernholt.repository.OneTimePasswordRepository;
import se.lernholt.repository.UserRepository;
import se.lernholt.util.GenerateCodeUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository            userRepository;
    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final PasswordEncoder           passwordEncoder;

    public void addUser(User user) {
        var password = user.getPassword();
        var encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void auth(User user) {
        var username = user.getUsername();
        Optional<User> storedUserOpt = userRepository.findByUsername(username);
        if (storedUserOpt.isPresent()) {
            var password = user.getPassword();
            var encodedPassword = storedUserOpt.get()
                    .getPassword();
            if (passwordEncoder.matches(password, encodedPassword)) {
                renewOneTimePassword(username);
                return;
            }
            throw new BadCredentialsException("Bad credentials; password doesn't match.");
        }
        throw new BadCredentialsException("Bad credentials; user doesn't exist.");
    }

    private void renewOneTimePassword(String username) {
        var code = GenerateCodeUtil.generateCode();
        var oneTimePasswordOpt = oneTimePasswordRepository.findByUsername(username);
        if (oneTimePasswordOpt.isPresent()) {
            var oneTimePassword = oneTimePasswordOpt.get();
            oneTimePassword.setCode(code);
            return;
        }
        var oneTimePassword = OneTimePassword.builder()
                .username(username)
                .code(code)
                .build();
        oneTimePasswordRepository.save(oneTimePassword);
    }

    public boolean check(OneTimePassword oneTimePassword) {
        var storedOneTimePasswordOpt = oneTimePasswordRepository.findByUsername(oneTimePassword.getUsername());
        if (storedOneTimePasswordOpt.isPresent()) {
            return StringUtils.equals(oneTimePassword.getCode(), storedOneTimePasswordOpt.get()
                    .getCode());
        }
        return false;
    }
}
