package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.AttendanceStatus;
import lombok.Data;

@Data
public class StudentSessionAttendanceDTO {
    private Long sessionId;
    private String sessionDate;
    private String sessionStart;
    private String sessionEnd;
    private AttendanceStatus status;
}

