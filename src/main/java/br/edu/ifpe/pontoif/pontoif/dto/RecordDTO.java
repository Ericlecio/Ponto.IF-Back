package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RecordDTO {
    private UUID id;
    private UUID lesson;
    private LocalDateTime date;
    private UUID user;
}
