package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = { DisciplineMapper.class, CourseMapper.class })
public interface ClassroomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "course")
    @Mapping(target = "disciplines", source = "disciplines")
    Classroom toEntity(ClassroomDTO classroomDTO);


    @Mapping(target = "course", source = "course.id")
    @Mapping(target = "disciplines", source = "disciplines")
    ClassroomDTO toDTO(Classroom classroom);

    Classroom fromId(UUID id);
    java.util.UUID toId(Classroom classroom);
}
