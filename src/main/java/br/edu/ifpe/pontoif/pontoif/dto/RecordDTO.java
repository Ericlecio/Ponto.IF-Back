package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RecordDTO {
    private UUID id;
    private Lesson lesson;
    private LocalDateTime date;
    private User user;
}
