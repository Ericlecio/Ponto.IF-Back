package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "biometrics")
@Data
public class Biometric {
    @Id
    private Long id; //ID do sensor

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Lob
    private byte[] template;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
