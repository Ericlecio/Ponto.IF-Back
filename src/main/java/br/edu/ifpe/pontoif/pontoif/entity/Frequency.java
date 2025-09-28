package br.edu.ifpe.pontoif.pontoif.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "frequencys")
public class Frequency {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     public UUID id;
}
