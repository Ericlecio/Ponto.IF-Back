package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ClassroomMapper.class, LessonMapper.class })
public interface DisciplineMapper {

    @Mapping(target = "classroom.id", source = "classroom")
    @Mapping(target = "lessons", source = "lessons")
    Discipline toEntity(DisciplineDTO dto);

    @Mapping(target = "classroom", source = "classroom.id")
    @Mapping(target = "lessons", source = "lessons")
    DisciplineDTO toDTO(Discipline entity);

    Discipline fromId(UUID id);
    default UUID toId(Discipline discipline) {
        return discipline != null ? discipline.getId() : null;
    }
}
