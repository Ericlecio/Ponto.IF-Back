package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "records")
@Data
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //No dto ID do aluno dentro do sensor


    private List<Biometric> biometrics;
}
