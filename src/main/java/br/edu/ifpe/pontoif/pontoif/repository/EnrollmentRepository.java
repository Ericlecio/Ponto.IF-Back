package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.dto.EnrollmentDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findAllByOffering_Id(Long offeringId);
    List<Enrollment> getEnrollmentByStudent_Id(UUID studentID);
}
