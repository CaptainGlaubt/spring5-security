package se.lernholt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.lernholt.model.OneTimePassword;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, String> {
    Optional<OneTimePassword> findByUsername(String username);
}
