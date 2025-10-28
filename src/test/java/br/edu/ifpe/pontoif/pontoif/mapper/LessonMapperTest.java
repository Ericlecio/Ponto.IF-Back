package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LessonMapperTest {

    private final LessonMapper lessonMapper = Mappers.getMapper(LessonMapper.class);

    @Test
    void shouldMapLessonDTOToEntity() {
        // Given
        LessonDTO dto = new LessonDTO();
        dto.setDayOfWeek(DayOfWeek.MONDAY);
        dto.setStartTime(LocalTime.of(8, 0));
        dto.setEndTime(LocalTime.of(10, 0));

        // When
        Lesson entity = lessonMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(entity.getStartTime()).isEqualTo(LocalTime.of(8, 0));
        assertThat(entity.getEndTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void shouldMapLessonEntityToDTO() {
        // Given
        Lesson entity = new Lesson();
        entity.setDayOfWeek(DayOfWeek.TUESDAY);
        entity.setStartTime(LocalTime.of(14, 0));
        entity.setEndTime(LocalTime.of(16, 0));

        // When
        LessonDTO dto = lessonMapper.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY);
        assertThat(dto.getStartTime()).isEqualTo(LocalTime.of(14, 0));
        assertThat(dto.getEndTime()).isEqualTo(LocalTime.of(16, 0));
    }

    @Test
    void shouldMapFromId() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        Lesson entity = lessonMapper.fromId(id);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(id);
    }

    @Test
    void shouldMapToId() {
        // Given
        Lesson entity = new Lesson();
        UUID id = UUID.randomUUID();
        entity.setId(id);

        // When
        UUID resultId = lessonMapper.toId(entity);

        // Then
        assertThat(resultId).isEqualTo(id);
    }
}
