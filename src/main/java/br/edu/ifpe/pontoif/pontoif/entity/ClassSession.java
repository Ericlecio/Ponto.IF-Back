package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "class_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_id")
    private SubjectOffering offering;

    @Column(name = "session_start", nullable = false)
    private Instant sessionStart;

    @Column(name = "session_end")
    private Instant sessionEnd;

    @Column(name = "external_code")
    private String externalCode;

    private String notes;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }
}

