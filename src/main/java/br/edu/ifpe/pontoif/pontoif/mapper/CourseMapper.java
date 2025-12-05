package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "acronym", target = "code")
    Course toEntity(CourseDTO dto);

    CourseDTO toDTO(Course entity);

    default Course fromId(UUID id) {
        if (id == null) return null;
        Course c = new Course();
        c.setId(id);
        return c;
    }

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "acronym", target = "code")
    void updateEntityFromDTO(CourseDTO dto, @MappingTarget Course entity);

    default UUID toId(Course course) {
        return course != null ? course.getId() : null;
    }

    default List<UUID> toIdList(List<Course> list) {
        if (list == null) return null;
        return list.stream().map(this::toId).filter(Objects::nonNull).toList();
    }

    default List<Course> fromIdList(List<UUID> list) {
        if (list == null) return null;
        return list.stream().map(this::fromId).toList();
    }
}