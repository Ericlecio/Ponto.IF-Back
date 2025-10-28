//package br.edu.ifpe.pontoif.pontoif.mapper;
//
//import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
//import br.edu.ifpe.pontoif.pontoif.entity.Record;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class RecordMapperTest {
//
//    private final RecordMapper recordMapper = Mappers.getMapper(RecordMapper.class);
//
//    @Test
//    void shouldMapRecordDTOToEntity() {
//        // Given
//        RecordDTO dto = new RecordDTO();
//        dto.setDate(LocalDateTime.now());
//        dto.setUser(UUID.randomUUID());
//        dto.setLesson(UUID.randomUUID());
//
//        // When
//        Record entity = recordMapper.toEntity(dto);
//
//        // Then
//        assertThat(entity).isNotNull();
//        assertThat(entity.getDate()).isNotNull();
//    }
//
//    @Test
//    void shouldMapRecordEntityToDTO() {
//        // Given
//        Record entity = new Record();
//        entity.setDate(LocalDateTime.now());
//
//        // When
//        RecordDTO dto = recordMapper.toDTO(entity);
//
//        // Then
//        assertThat(dto).isNotNull();
//        assertThat(dto.getDate()).isNotNull();
//    }
//
//    @Test
//    void shouldMapFromId() {
//        // Given
//        UUID id = UUID.randomUUID();
//
//        // When
//        Record entity = recordMapper.fromId(id);
//
//        // Then
//        assertThat(entity).isNotNull();
//        assertThat(entity.getId()).isEqualTo(id);
//    }
//
//    @Test
//    void shouldMapToId() {
//        // Given
//        Record entity = new Record();
//        UUID id = UUID.randomUUID();
//        entity.setId(id);
//
//        // When
//        UUID resultId = recordMapper.toId(entity);
//
//        // Then
//        assertThat(resultId).isEqualTo(id);
//    }
//}
