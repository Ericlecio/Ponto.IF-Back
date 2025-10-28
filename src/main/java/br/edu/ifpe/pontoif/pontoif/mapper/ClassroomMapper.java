package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = { CourseMapper.class, DisciplineMapper.class })
public interface ClassroomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "course")
    @Mapping(target = "disciplines", source = "disciplines")
    Classroom toEntity(ClassroomDTO dto);

    @Mapping(target = "course", source = "course.id")
    @Mapping(target = "disciplines", source = "disciplines")
    ClassroomDTO toDTO(Classroom entity);

    default Course fromId(UUID id) {
        if (id == null) return null;
        Course c = new Course();
        c.setId(id);
        return c;
    }

    default UUID toId(Course course) {
        return course != null ? course.getId() : null;
    }

    default Discipline fromDisciplineId(UUID id) {
        if (id == null) return null;
        Discipline d = new Discipline();
        d.setId(id);
        return d;
    }

    default UUID toId(Discipline d) {
        return d != null ? d.getId() : null;
    }

    default List<UUID> toIdList(List<Discipline> list) {
        if (list == null) return null;
        return list.stream().map(this::toId).filter(Objects::nonNull).toList();
    }

    default List<Discipline> fromIdList(List<UUID> list) {
        if (list == null) return null;
        return list.stream().map(this::fromDisciplineId).toList();
    }
}