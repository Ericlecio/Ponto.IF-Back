package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClassroomMapperTest {

    @Autowired
    private ClassroomMapper classroomMapper;

    @Test
    void shouldMapClassroomDTOToEntity() {
        // Given
        ClassroomDTO dto = new ClassroomDTO();
        dto.setCode("CC-2024-1");

        // When
        Classroom entity = classroomMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getCode()).isEqualTo("CC-2024-1");
    }

    @Test
    void shouldMapClassroomEntityToDTO() {
        // Given
        Classroom entity = new Classroom();
        entity.setCode("SI-2024-2");

        // When
        ClassroomDTO dto = classroomMapper.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getCode()).isEqualTo("SI-2024-2");
    }

    @Test
    void shouldMapFromId() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        Classroom entity = classroomMapper.fromId(id);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id);
    }

    @Test
    void shouldMapToId() {
        // Given
        Classroom entity = new Classroom();
        UUID id = UUID.randomUUID();
        entity.setId(id);

        // When
        UUID resultId = classroomMapper.toId(entity);

        // Then
        assertThat(resultId).isEqualTo(id);
    }
}
