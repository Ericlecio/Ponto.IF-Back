package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DisciplineRepositoryTest {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Test
    void shouldSaveAndFindDisciplineById() {
        // Given
        Discipline discipline = new Discipline();
        discipline.setName("Programação Orientada a Objetos");
        discipline.setWorkload(60);

        // When
        Discipline savedDiscipline = disciplineRepository.save(discipline);

        // Then
        assertThat(savedDiscipline.getId()).isNotNull();
        assertThat(savedDiscipline.getName()).isEqualTo("Programação Orientada a Objetos");
        assertThat(savedDiscipline.getWorkload()).isEqualTo(60);
    }

    @Test
    void shouldFindDisciplineByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();
        Discipline discipline = new Discipline();
        discipline.setCorrelationId(correlationId);
        discipline.setName("Estrutura de Dados");
        discipline.setWorkload(80);
        disciplineRepository.save(discipline);

        // When
        Discipline foundDiscipline = disciplineRepository.findByCorrelationId(correlationId);

        // Then
        assertThat(foundDiscipline).isNotNull();
        assertThat(foundDiscipline.getCorrelationId()).isEqualTo(correlationId);
        assertThat(foundDiscipline.getName()).isEqualTo("Estrutura de Dados");
    }

    @Test
    void shouldReturnTrueIfDisciplineExistsByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();
        Discipline discipline = new Discipline();
        discipline.setCorrelationId(correlationId);
        discipline.setName("Banco de Dados");
        discipline.setWorkload(40);
        disciplineRepository.save(discipline);

        // When
        boolean exists = disciplineRepository.existsByCorrelationId(correlationId);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseIfDisciplineDoesNotExistByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();

        // When
        boolean exists = disciplineRepository.existsByCorrelationId(correlationId);

        // Then
        assertThat(exists).isFalse();
    }
}
