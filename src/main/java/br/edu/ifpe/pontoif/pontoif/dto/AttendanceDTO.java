package br.edu.ifpe.pontoif.pontoif.dto;


import br.edu.ifpe.pontoif.pontoif.entity.AttendanceStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
public class AttendanceDTO {
    private Long id;
    private Instant createdAt;
    private Long sessionId;
    private UUID studentId;
    private byte[] biometricHash;
    private Instant recordedAt;
    private AttendanceStatus status;
    private Double confidence;
    private Map<String, Object> metadata;

    private Long offeringId;
}
