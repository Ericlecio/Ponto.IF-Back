package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "classrooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Classroom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(nullable = false, name = "code")
    private String name;

    private String location;

    @PrePersist void prePersist(){ if(createdAt==null) createdAt = Instant.now(); }
}

