package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ClassSessionRepository extends JpaRepository <ClassSession, Long> {
    List<ClassSession> findAllByOffering_Id(Long offeringId);

    @Query("""
        SELECT cs FROM ClassSession cs
        WHERE cs.sessionStart <= :now
          AND (cs.sessionEnd IS NULL OR cs.sessionEnd > :now)
    """)
    List<ClassSession> findAllActiveSessions(@Param("now") Instant now);
}