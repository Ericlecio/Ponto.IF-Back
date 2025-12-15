package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.StudentAttendanceReportDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceStatus;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceReportMapper {

    default StudentAttendanceReportDTO toDTO(
            Enrollment enrollment,
            ClassSession session,
            AttendanceRecord record,
            long totalSessions,
            long attendedSessions,
            double percentage
    ) {

        return StudentAttendanceReportDTO.builder()
                .studentId(enrollment.getStudent().getId())
                .studentName(enrollment.getStudent().getName())
                .studentEmail(enrollment.getStudent().getEmail())
                .studentRegistration(enrollment.getStudent().getRegistration())
                .sessionId(session.getId())
                .attendanceStatus(
                        record != null
                                ? record.getStatus().name()
                                : AttendanceStatus.ABSENT.name()
                )
                .totalSessions(totalSessions)
                .attendedSessions(attendedSessions)
                .attendancePercentage(percentage)
                .build();
    }
}
