package br.edu.ifpe.pontoif.pontoif.service;


import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.dto.AttendanceRequestDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import br.edu.ifpe.pontoif.pontoif.repository.AttendanceRecordRepository;
import br.edu.ifpe.pontoif.pontoif.repository.ClassSessionRepository;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final ClassSessionRepository classSessionRepository;
    private final UserRepository userRepository;

    public List<AttendanceRecord> registerAttendances(AttendanceRequestDTO dto) {

        List<AttendanceRecord> savedRecords = new ArrayList<>();

        for (AttendanceDTO item : dto.getAttendances()) {

            ClassSession session = classSessionRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Session not found"));

            User student = userRepository.findById(item.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            AttendanceRecord record = AttendanceRecord.builder()
                    .session(session)
                    .student(student)
                    .biometricHash(item.getBiometricHash())
                    .recordedAt(item.getRecordedAt())
                    .status(item.getStatus())
                    .confidence(item.getConfidence())
                    .metadata(item.getMetadata())
                    .build();

            AttendanceRecord saved = attendanceRecordRepository.save(record);
            savedRecords.add(saved);
        }

        return savedRecords;
    }

    public List<AttendanceDTO> getAttendanceByOffering(Long offeringId) {

        List<AttendanceRecord> records = attendanceRecordRepository.findAllByOfferingId(offeringId);

        return records.stream().map(r ->
                AttendanceDTO.builder()
                        .id(r.getId())
                        .session(r.getSession())
                        .student(r.getStudent())
                        .recordedAt(r.getRecordedAt())
                        .status(r.getStatus())
                        .confidence(r.getConfidence())
                        .metadata(r.getMetadata())
                        .build()
        ).toList();
    }
}
