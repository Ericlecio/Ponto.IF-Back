package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BiometricMatchResultDTO {
    private UUID biometricId;
    private UUID studentId;
    private double score;
}
