package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BiometricRepository extends JpaRepository <Biometric, UUID> {
    List<Biometric> findAllByUser_Role(Role role);

    @Query("""
    select distinct b
    from Biometric b
    join b.user u
    join Enrollment e on e.student = u
    join ClassSession s on s.offering = e.offering
    where s.id = :sessionId
      and e.status = br.edu.ifpe.pontoif.pontoif.entity.EnrollmentStatus.ACTIVE
      and u.role = :role
""")
    List<Biometric> findBiometricsBySessionAndRole(
            @Param("sessionId") Long sessionId,
            @Param("role") Role role
    );
}
