package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BiometricDTO {
    private UUID id;
    private byte[] image;
    private UUID user;
}