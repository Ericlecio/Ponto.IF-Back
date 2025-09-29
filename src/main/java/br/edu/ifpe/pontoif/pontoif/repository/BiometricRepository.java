package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometricRepository extends JpaRepository <Biometric, Long> {
}
