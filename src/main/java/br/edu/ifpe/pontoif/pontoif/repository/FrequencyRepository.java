package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FrequencyRepository extends JpaRepository<Frequency, UUID> {
}
