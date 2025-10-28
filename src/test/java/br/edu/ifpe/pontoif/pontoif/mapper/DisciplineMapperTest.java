package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DisciplineMapperTest {

    private final DisciplineMapper disciplineMapper = Mappers.getMapper(DisciplineMapper.class);

    @Test
    void shouldMapDisciplineDTOToEntity() {
        // Given
        DisciplineDTO dto = new DisciplineDTO();
        dto.setName("Matemática Discreta");
        dto.setWorkload(60);
        dto.setClassroom(UUID.randomUUID());

        // When
        Discipline entity = disciplineMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("Matemática Discreta");
        assertThat(entity.getWorkload()).isEqualTo(60);
    }

    @Test
    void shouldMapDisciplineEntityToDTO() {
        // Given
        Discipline entity = new Discipline();
        entity.setName("Algoritmos");
        entity.setWorkload(80);

        // When
        DisciplineDTO dto = disciplineMapper.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("Algoritmos");
        assertThat(dto.getWorkload()).isEqualTo(80);
    }

    @Test
    void shouldMapFromId() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        Discipline entity = disciplineMapper.fromId(id);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id);
    }

    @Test
    void shouldMapToId() {
        // Given
        Discipline entity = new Discipline();
        UUID id = UUID.randomUUID();
        entity.setId(id);

        // When
        UUID resultId = disciplineMapper.toId(entity);

        // Then
        assertThat(resultId).isEqualTo(id);
    }
}
