package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CourseMapperTest {

    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @Test
    void shouldMapCourseDTOToEntity() {
        // Given
        CourseDTO dto = new CourseDTO();
        dto.setId(java.util.UUID.randomUUID());
        dto.setDuration(12);

        // When
        Course entity = courseMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getCorrelationId()).isEqualTo(dto.getId());
        assertThat(entity.getId()).isNull(); // ignored
        assertThat(entity.getClassrooms()).isNull(); // ignored
        assertThat(entity.getDurationInMonths()).isEqualTo(12);
    }
}
