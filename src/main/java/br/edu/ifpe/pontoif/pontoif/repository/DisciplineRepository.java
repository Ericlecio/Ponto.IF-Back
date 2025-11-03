package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
    boolean existsByCorrelationId(UUID id);

    Discipline findByCorrelationId(UUID id);

    @Query("SELECT d FROM Discipline d JOIN d.lessons l JOIN l.records r JOIN r.user us WHERE us.id = :userId")
    List<Discipline> findByUserId(@Param("userId") UUID userId);
}
