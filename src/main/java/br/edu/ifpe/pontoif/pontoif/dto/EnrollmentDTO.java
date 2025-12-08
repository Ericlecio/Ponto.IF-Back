package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.EnrollmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EnrollmentDTO {
    private UUID enrollmentId;
    private UUID studentId;
    private EnrollmentStatus status;
    private Long subjctOfferingId;
}
