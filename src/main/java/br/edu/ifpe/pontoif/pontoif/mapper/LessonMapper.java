package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(LessonDTO lessonDTO);

    LessonDTO toDTO(Lesson lesson);
}
