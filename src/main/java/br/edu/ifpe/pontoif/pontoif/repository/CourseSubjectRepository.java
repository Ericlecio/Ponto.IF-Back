package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.CourseSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseSubjectRepository extends JpaRepository<CourseSubject, Long>{
    @Query("SELECT cs FROM CourseSubject cs WHERE cs.course.id = :courseId AND cs.subject.id = :subjectId")
    Optional<CourseSubject> findByCurseIdAndSubjectId(UUID courseId, UUID subjectId);
}
