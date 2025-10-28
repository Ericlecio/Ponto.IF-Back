package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.lang.ScopedValue;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByCorrelationId(UUID correlationId);
    Boolean existsByCorrelationId(UUID correlationId);
    Optional<User> findByEmail(String email);
}
