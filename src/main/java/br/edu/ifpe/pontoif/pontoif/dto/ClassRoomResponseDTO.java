package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ClassRoomResponseDTO {
    private UUID id;
    private String name;
    private String location;
    private Instant createdAt;
}
