package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { RecordMapper.class })
public interface LessonMapper {

    @Mapping(target = "discipline.id", source = "discipline")
    @Mapping(target = "records", source = "records")
    Lesson toEntity(LessonDTO lessonDTO);

    @Mapping(target = "discipline", source = "discipline.id")
    @Mapping(target = "records", source = "records")
    LessonDTO toDTO(Lesson lesson);
}
