package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequestDTO {

    private Long offeringId;
    private Instant timestamp;
    private String type;

    private List<AttendanceDTO> attendances;
}
