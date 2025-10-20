package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ClassroomDTO {
    private UUID id;
    private String code;
    private UUID courseId;
    private List<UUID> disciplineIds;

    public void ClassroomDisciplinesDTO(UUID id, List<UUID> disciplineIds) {
        this.id = id;
        this.disciplineIds = disciplineIds;
    }
}
