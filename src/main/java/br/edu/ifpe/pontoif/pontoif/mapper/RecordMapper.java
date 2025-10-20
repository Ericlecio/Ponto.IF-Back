package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "user")
    @Mapping(target = "lesson.id", source = "lesson")
    Record toEntity(RecordDTO recordDTO);

    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "lesson", source = "lesson.id")
    RecordDTO toDTO(Record record);

    Record fromId(UUID id);
    default UUID toId(Record record) {
        return record != null ? record.getId() : null;
    }
}