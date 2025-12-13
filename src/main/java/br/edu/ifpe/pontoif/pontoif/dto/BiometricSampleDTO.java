package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BiometricSampleDTO {
    private Role role;
    private byte[] image;
    private Long sessionId;
}