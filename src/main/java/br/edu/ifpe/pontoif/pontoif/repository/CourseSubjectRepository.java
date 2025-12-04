package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.CourseSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSubjectRepository extends JpaRepository<CourseSubject, Long>{
}
