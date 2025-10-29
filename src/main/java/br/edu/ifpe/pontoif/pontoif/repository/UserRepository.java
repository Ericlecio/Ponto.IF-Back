package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByCorrelationId(UUID correlationId);
    Boolean existsByCorrelationId(UUID correlationId);
    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndRole(UUID id, Role role);

    List<User> findByRole(Role role);

    @Query("SELECT us FROM Discipline d JOIN d.lessons l JOIN l.records r JOIN r.user us WHERE d.id = :disciplineId")
    List<User> findByDisciplineId(@Param("disciplineId") UUID userId);
}
