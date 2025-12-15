package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StudentAttendanceDetailsDTO {

    private UUID studentId;
    private String studentName;
    private String registration;

    private Long offeringId;
    private String subjectName;
    private String teacherName;

    private int totalSessions;
    private int presents;
    private int absents;

    private List<StudentSessionAttendanceDTO> sessions;
}