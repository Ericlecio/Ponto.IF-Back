package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.dto.StudentAttendanceReportDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceStatus;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import br.edu.ifpe.pontoif.pontoif.mapper.AttendanceMapper;
import br.edu.ifpe.pontoif.pontoif.repository.AttendanceRecordRepository;
import br.edu.ifpe.pontoif.pontoif.repository.ClassSessionRepository;
import br.edu.ifpe.pontoif.pontoif.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository repository;
    private final AttendanceMapper mapper;
    private final EnrollmentRepository enrollmentRepository;
    private final ClassSessionRepository classSessionRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Transactional
    public void registerAttendance (AttendanceDTO dto) {
        boolean alreadyPresent =
                repository.existsBySession_IdAndStudent_IdAndStatus(
                        dto.getSessionId(),
                        dto.getStudentId(),
                        AttendanceStatus.PRESENT
                );

        if (alreadyPresent) {
            log.warn(
                    "⚠️ Attendance already registered → student={} session={}",
                    dto.getStudentId(),
                    dto.getSessionId()
            );
            return;
        }

        AttendanceRecord record = mapper.toEntity(dto);
        repository.save(record);
    }

    public List<AttendanceDTO> getAttendanceByOffering(Long offeringId) {
        return repository.findAllByOfferingId(offeringId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendanceBySession(Long sessionId){
        return repository.findAllBySession_Id(sessionId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<StudentAttendanceReportDTO> generateAttendanceReport(Long offeringId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByOffering_Id(offeringId);
        List<ClassSession> sessions = classSessionRepository.findAllByOffering_Id(offeringId);
        List<AttendanceRecord> attendanceRecords = repository.findAllByOfferingId(offeringId);

        Map<UUID, Map<Long, AttendanceRecord>> attendanceByStudentAndSession = attendanceRecords.stream()
                .collect(Collectors.groupingBy(
                        ar -> ar.getStudent().getId(),
                        Collectors.toMap(
                                ar -> ar.getSession().getId(),
                                ar -> ar,
                                (existing, replacement) -> existing
                        )
                ));

        List<StudentAttendanceReportDTO> reportList = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            UUID studentId = enrollment.getStudent().getId();
            Map<Long, AttendanceRecord> studentAttendance = attendanceByStudentAndSession.getOrDefault(studentId, new HashMap<>());
            long totalSessions = sessions.size();
            long attendedSessions = studentAttendance.values().stream()
                    .filter(ar -> ar.getStatus() == AttendanceStatus.PRESENT ||
                                  ar.getStatus() == AttendanceStatus.LATE)
                    .count();

            double attendancePercentage = totalSessions > 0
                    ? (attendedSessions * 100.0) / totalSessions
                    : 0.0;
            for (ClassSession session : sessions) {
                AttendanceRecord record = studentAttendance.get(session.getId());
                String status = record != null ? record.getStatus().name() : AttendanceStatus.ABSENT.name();

                StudentAttendanceReportDTO reportDTO = StudentAttendanceReportDTO.builder()
                        .studentId(studentId)
                        .studentName(enrollment.getStudent().getName())
                        .studentEmail(enrollment.getStudent().getEmail())
                        .studentRegistration(enrollment.getStudent().getRegistration())
                        .sessionId(session.getId())
                        .sessionDate(formatDate(session.getSessionStart()))
                        .sessionStart(formatTime(session.getSessionStart()))
                        .sessionEnd(session.getSessionEnd() != null ? formatTime(session.getSessionEnd()) : "")
                        .attendanceStatus(status)
                        .attendancePercentage(attendancePercentage)
                        .totalSessions(totalSessions)
                        .attendedSessions(attendedSessions)
                        .build();

                reportList.add(reportDTO);
            }
        }

        return reportList;
    }

    private String formatDate(Instant instant) {
        if (instant == null) return "";
        return instant.atZone(ZoneId.systemDefault()).format(DATE_FORMATTER);
    }

    private String formatTime(Instant instant) {
        if (instant == null) return "";
        return instant.atZone(ZoneId.systemDefault()).format(TIME_FORMATTER);
    }
}