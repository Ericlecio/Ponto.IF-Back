package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    @Mapping(target = "id", ignore = true)
    Classroom toEntity(ClassroomDTO classroomDTO);

    ClassroomDTO toDTO(Classroom classroom);

    Classroom fromId(UUID id);
    UUID toId(Classroom classroom);
}
