package no.smoky.magic.magicserver;

// Brukes ?

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import no.smoky.magic.magicserver.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}