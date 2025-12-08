package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {
    @Id
    private UUID id;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(nullable = false, name = "code")
    private String name;

    private String location;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
        if (id == null) id = UUID.randomUUID();
    }
}

