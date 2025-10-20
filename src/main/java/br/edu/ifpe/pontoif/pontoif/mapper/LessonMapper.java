package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = { RecordMapper.class })
public interface LessonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "discipline.id", source = "discipline")
    @Mapping(target = "records", source = "records")
    Lesson toEntity(LessonDTO dto);

    @Mapping(target = "discipline", source = "discipline.id")
    @Mapping(target = "records", source = "records")
    LessonDTO toDTO(Lesson entity);

    Lesson fromId(UUID id);
    default UUID toId(Lesson lesson) {
        return lesson != null ? lesson.getId() : null;
    }
}
