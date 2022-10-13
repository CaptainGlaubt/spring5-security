package se.lernholt.security.user;

import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import se.lernholt.repository.JpaUserRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private static final Supplier<UsernameNotFoundException> USER_NOT_FOUND = () -> new UsernameNotFoundException(
            "Problem ocurred during authentication.");

    private final JpaUserRepository                          userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).map(user -> new JpaUser(user)).orElseThrow(USER_NOT_FOUND);
    }
}
