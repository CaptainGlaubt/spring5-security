package se.lernholt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lernholt.domain.security.User;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
