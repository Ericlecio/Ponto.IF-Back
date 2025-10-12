package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository <Lesson, UUID> {

    @Query("""
                SELECT l FROM Lesson l
                WHERE l.id = :lessonId
                AND l.dayOfWeek = :dayOfWeek
                AND :currentTime BETWEEN l.startTime AND l.endTime
            """)
    Optional<Lesson> findIfActive(
            @Param("lessonId") UUID lessonId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("currentTime") LocalTime currentTime
    );
}
