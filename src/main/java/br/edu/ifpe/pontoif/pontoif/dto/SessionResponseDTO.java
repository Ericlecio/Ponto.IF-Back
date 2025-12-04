package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SessionResponseDTO {
    private Long id;
    private Instant createdAt;
    private Long offeringId;
    private Instant sessionStart;
    private Instant sessionEnd;
    private String externalCode;
    private String notes;
}
