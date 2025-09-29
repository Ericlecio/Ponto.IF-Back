package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CourseRepositoty extends JpaRepository<Course, UUID> {
}
