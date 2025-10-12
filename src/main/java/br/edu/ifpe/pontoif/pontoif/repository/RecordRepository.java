package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Record;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    @Query("""
                SELECT r FROM Record r
                JOIN FETCH r.lesson l
                WHERE r.user = :user
            """)
    List<Record> findAllByUserWithLessons(@Param("user") User user);
}
