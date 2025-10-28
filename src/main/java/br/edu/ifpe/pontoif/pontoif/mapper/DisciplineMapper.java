package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = { ClassroomMapper.class })
public interface DisciplineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classroom.id", source = "classroom")
    @Mapping(target = "lessons", source = "lessons")
    Discipline toEntity(DisciplineDTO dto);

    @Mapping(target = "classroom", source = "classroom.id")
    @Mapping(target = "lessons", source = "lessons")
    DisciplineDTO toDTO(Discipline entity);

    default Classroom fromId(UUID id) {
        if (id == null) return null;
        Classroom c = new Classroom();
        c.setId(id);
        return c;
    }

    default UUID toId(Classroom classroom) {
        return classroom != null ? classroom.getId() : null;
    }

    default Lesson fromLessonId(UUID id) {
        if (id == null) return null;
        Lesson l = new Lesson();
        l.setId(id);
        return l;
    }

    default UUID toId(Lesson lesson) {
        return lesson != null ? lesson.getId() : null;
    }

    default List<UUID> toIdList(List<Lesson> list) {
        if (list == null) return null;
        return list.stream().map(this::toId).filter(Objects::nonNull).toList();
    }

    default List<Lesson> fromIdList(List<UUID> list) {
        if (list == null) return null;
        return list.stream().map(this::fromLessonId).toList();
    }
}
