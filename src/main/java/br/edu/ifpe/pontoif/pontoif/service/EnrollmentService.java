package br.edu.ifpe.pontoif.pontoif.service;


import br.edu.ifpe.pontoif.pontoif.dto.EnrollmentDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import br.edu.ifpe.pontoif.pontoif.entity.SubjectOffering;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import br.edu.ifpe.pontoif.pontoif.mapper.EnrollmentMapper;
import br.edu.ifpe.pontoif.pontoif.repository.EnrollmentRepository;
import br.edu.ifpe.pontoif.pontoif.repository.SubjectOfferingRepository;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final SubjectOfferingRepository offeringRepository;
    private final UserRepository userRepository;


    public Optional<EnrollmentDTO> getEnrollmentById(UUID id) {
        return enrollmentRepository.findById(id).map(enrollmentMapper::toDTO);
    }

    public void registerEnrollment (EnrollmentDTO dto) {
        enrollmentRepository.save(enrollmentMapper.toEntity(dto));
    }

    public List<EnrollmentDTO> getAllEnrollment() {
        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::toDTO)
                .toList();
    }

    public Optional<EnrollmentDTO> updateEnrollment(final UUID id, final EnrollmentDTO dto) {

        return enrollmentRepository.findById(id).map(existing -> {

            SubjectOffering offering = offeringRepository.findById(dto.getSubjectOfferingId())
                    .orElseThrow(() -> new RuntimeException("Offering not found"));

            User student = userRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            enrollmentMapper.updateEntityFromDTO(dto, existing, offering, student);

            Enrollment saved = enrollmentRepository.save(existing);

            return enrollmentMapper.toDTO(saved);
        });
    }


    public boolean delete(final UUID id) {
        return enrollmentRepository.findById(id).map(enrollment -> {
            enrollmentRepository.delete(enrollment);
            return true;
        }).orElse(false);
    }
}
