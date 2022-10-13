package se.lernholt.security.user;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import se.lernholt.domain.security.Authority;
import se.lernholt.domain.security.User;

@Data
@RequiredArgsConstructor
public class JpaUser implements UserDetails {

    private static final long serialVersionUID = 6978543373838537784L;

    private final User        user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities().stream().map(JpaUser::toGrantedAuthority).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static GrantedAuthority toGrantedAuthority(Authority authority) {
        return () -> authority.getName();
    }
}
