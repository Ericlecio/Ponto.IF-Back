package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classrooms", ignore = true)
    @Mapping(target = "correlationId", source = "id")
    @Mapping(target = "durationInMonths", source = "duration")
    Course toEntity(CourseDTO dto);
}
