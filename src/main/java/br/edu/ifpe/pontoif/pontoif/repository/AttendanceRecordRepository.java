package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    @Query("SELECT a FROM AttendanceRecord a WHERE a.session.offering.id = :offeringId")
    List<AttendanceRecord> findAllByOfferingId(@Param("offeringId") Long offeringId);

    List<AttendanceRecord> findAllBySession_Id(Long sessionId);

    boolean existsBySession_IdAndStudent_IdAndStatus(
            Long sessionId,
            UUID studentId,
            AttendanceStatus status
    );
}
