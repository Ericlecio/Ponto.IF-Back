package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.SubjectDTO;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import br.edu.ifpe.pontoif.pontoif.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = { CourseMapper.class })
public interface SubjectMapper {

    SubjectResponseDTO toDTO(Subject entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Subject toEntity(SubjectDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "courses", source = "courses")
    void updateEntityFromDTO(SubjectDTO dto, @MappingTarget Subject entity);

    default Course fromId(UUID id) {
        if (id == null) return null;
        Course c = new Course();
        c.setId(id);
        return c;
    }

    default UUID toId(Course c) {
        return c != null ? c.getId() : null;
    }
}