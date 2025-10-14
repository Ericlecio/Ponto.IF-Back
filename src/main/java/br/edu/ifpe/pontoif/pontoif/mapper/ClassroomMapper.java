package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {


    @Mapping(target = "course", source = "courseId", qualifiedByName = "mapIdToCourse")
    Classroom toEntity(ClassroomDTO classroomDTO);

    @Mapping(target = "courseId", source = "course.id")
    ClassroomDTO toDTO(Classroom classroom);


    @Named("mapIdToCourse")
    default Course mapIdToCourse(UUID id) {
        if (id == null) return null;
        Course course = new Course();
        course.setId(id);
        return course;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClassroomDTO classroomDTO, @MappingTarget Classroom classroom);

    default Classroom fromId(UUID id) {
        if (id == null) return null;
        Classroom classroom = new Classroom();
        classroom.setId(id);
        return classroom;
    }

    default UUID toId(Classroom classroom) {
        return classroom != null ? classroom.getId() : null;
    }


    @Named("mapIdsToDisciplines")
    default List<Discipline> mapIdsToDisciplines(List<UUID> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Discipline discipline = new Discipline();
            discipline.setId(id);
            return discipline;
        }).collect(Collectors.toList());
    }


    @Named("mapDisciplinesToIds")
    default List<UUID> mapDisciplinesToIds(List<Discipline> disciplines) {
        if (disciplines == null) return null;
        return disciplines.stream()
                .map(Discipline::getId)
                .collect(Collectors.toList());
    }
}
