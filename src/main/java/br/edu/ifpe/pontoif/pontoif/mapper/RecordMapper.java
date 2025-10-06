package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import org.mapstruct.Mapper;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(target = "user.id", source = "user")
    @Mapping(target = "lesson.id", source = "lesson")
    Record toEntity(RecordDTO recordDTO);

    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "lesson", source = "lesson.id")
    RecordDTO toDTO(Record record);
}


