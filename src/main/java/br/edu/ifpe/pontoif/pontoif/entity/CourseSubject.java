package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"courses_id", "subject_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id")
    private Course course;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
}

