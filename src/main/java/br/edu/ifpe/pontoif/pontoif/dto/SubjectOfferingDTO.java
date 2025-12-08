package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
public class SubjectOfferingDTO {
    private Long id;
    private Instant createdAt;
    private UUID courseId;
    private UUID subjectId;
    private Long classroomId;
    private UUID teacherId;
    private String term;
    private Map<String, Object> schedule;
}
