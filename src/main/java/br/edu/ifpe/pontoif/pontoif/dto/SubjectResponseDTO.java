package br.edu.ifpe.pontoif.pontoif.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponseDTO {
    private UUID id;
    @NotNull
    private List<CourseDTO> courses;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    private String code;
    private String description;
}
