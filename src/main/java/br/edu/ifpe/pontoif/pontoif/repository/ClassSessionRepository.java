package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {
    List<ClassSession> findAllByOffering_Id(Long offeringId);

    @Query("""
                SELECT cs FROM ClassSession cs
                WHERE cs.sessionStart <= :now
                  AND (cs.sessionEnd IS NULL OR cs.sessionEnd > :now)
            """)
    List<ClassSession> findAllActiveSessions(@Param("now") Instant now);

    @Query("""
                SELECT cs
                FROM ClassSession cs
                JOIN cs.offering o
                JOIN o.courseSubject csj
                JOIN csj.subject s
                WHERE s.id = :subjectId
            """)
    List<ClassSession> findAllBySubjectId(@Param("subjectId") UUID subjectId);
}