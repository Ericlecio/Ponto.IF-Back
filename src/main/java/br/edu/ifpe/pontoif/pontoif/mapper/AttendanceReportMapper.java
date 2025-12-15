package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.StudentAttendanceDetailsDTO;
import br.edu.ifpe.pontoif.pontoif.dto.StudentSessionAttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AttendanceReportMapper {

    default StudentAttendanceDetailsDTO toDetailsDTO(
            Enrollment enrollment,
            List<ClassSession> sessions,
            List<AttendanceRecord> records,
            List<StudentSessionAttendanceDTO> sessionDetails,
            int presents,
            int absents
    ) {

        StudentAttendanceDetailsDTO dto = new StudentAttendanceDetailsDTO();

        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getName());
        dto.setRegistration(enrollment.getStudent().getRegistration());

        dto.setOfferingId(enrollment.getOffering().getId());
        dto.setTeacherName(enrollment.getOffering().getTeacher().getName());

        dto.setTotalSessions(sessions.size());
        dto.setPresents(presents);
        dto.setAbsents(absents);
        dto.setSessions(sessionDetails);

        return dto;
    }

    default String formatDate(Instant instant) {
        if (instant == null) return "";
        return instant.atZone(java.time.ZoneId.systemDefault()).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    default String formatTime(Instant instant) {
        if (instant == null) return "";
        return instant.atZone(java.time.ZoneId.systemDefault()).format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }
}