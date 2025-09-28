package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "disciplines")
public class Discipline  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;
}
