package br.edu.ifpe.pontoif.pontoif.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ClassroomDTO {
    private UUID id;
    private String code;
    private UUID course;
    private List<UUID> disciplines;
}
