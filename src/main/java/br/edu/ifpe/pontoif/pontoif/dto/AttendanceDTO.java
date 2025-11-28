package br.edu.ifpe.pontoif.pontoif.dto;


import br.edu.ifpe.pontoif.pontoif.entity.AttendanceStatus;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long id;
    private Instant createdAt;
    private ClassSession session;
    private User student;
    private byte[] biometricHash;
    private Instant recordedAt;
    private AttendanceStatus status;
    private Double confidence;
    private Map<String, Object> metadata;
}
