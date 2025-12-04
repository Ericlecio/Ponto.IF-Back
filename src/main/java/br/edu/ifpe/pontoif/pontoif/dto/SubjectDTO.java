package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SubjectDTO {
    private UUID id;
    @NotNull
    private List<UUID> courses;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    private String code;
    private String description;
}
