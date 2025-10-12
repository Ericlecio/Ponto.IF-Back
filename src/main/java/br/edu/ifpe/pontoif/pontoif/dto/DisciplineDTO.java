package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DisciplineDTO {
    private UUID id;
    private UUID correlationId;
    private String name;
    private Integer workload;
    private UUID classroom;
    private List<UUID> lessons;
}
