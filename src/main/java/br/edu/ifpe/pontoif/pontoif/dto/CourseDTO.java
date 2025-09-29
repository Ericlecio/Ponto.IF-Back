package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CourseDTO {
    private UUID id;
    private String name;
    private String acronym;
    private Integer duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
