package br.edu.ifpe.pontoif.pontoif.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassRoomRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String location;
}
