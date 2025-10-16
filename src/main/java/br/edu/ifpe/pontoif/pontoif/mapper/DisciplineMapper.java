package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = { ClassroomMapper.class, LessonMapper.class })
public interface DisciplineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classroom.id", source = "classroom")
    @Mapping(target = "lessons", source = "lessons")
    Discipline toEntity(DisciplineDTO dto);

    @Mapping(target = "classroom", source = "classroom.id")
    @Mapping(target = "lessons", source = "lessons")
    DisciplineDTO toDTO(Discipline entity);

    Discipline fromId(UUID id);
    UUID toId(Discipline discipline);
}
