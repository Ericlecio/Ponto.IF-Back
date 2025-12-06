package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseSubjectRequestDTO {
    private UUID courseId;
    private UUID subjectId;
}
