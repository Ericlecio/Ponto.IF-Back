package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceReportDTO {
    private UUID studentId;
    private String studentName;
    private String studentEmail;
    private String studentRegistration;
    private Long sessionId;
    private String sessionDate;
    private String sessionStart;
    private String sessionEnd;
    private String attendanceStatus;
    private Double attendancePercentage;
    private Long totalSessions;
    private Long attendedSessions;
}

