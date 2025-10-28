package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "biometrics")
@Data
public class Biometric {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private byte[] template;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "biometric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records;
}
