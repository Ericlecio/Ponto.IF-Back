package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ClassRoomResponseDTO {
    private Long id;
    private String name;
    private String location;
    private Instant createdAt;
}
