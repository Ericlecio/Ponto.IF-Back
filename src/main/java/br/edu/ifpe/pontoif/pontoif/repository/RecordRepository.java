package br.edu.ifpe.pontoif.pontoif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ifpe.pontoif.pontoif.entity.Record;


import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
}
