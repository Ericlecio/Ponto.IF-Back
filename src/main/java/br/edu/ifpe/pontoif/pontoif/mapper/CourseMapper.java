package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classrooms", ignore = true)
    @Mapping(target = "correlationId", source = "id")
    Course toEntity(CourseDTO dto);


    CourseDTO toDTO(Course entity);

    Course fromId(UUID id);
    UUID toId(Course course);
}
