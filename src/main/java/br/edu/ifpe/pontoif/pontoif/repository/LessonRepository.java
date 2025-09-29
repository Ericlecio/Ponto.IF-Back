package br.edu.ifpe.pontoif.pontoif.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository <LessonRepository, UUID> {
}
