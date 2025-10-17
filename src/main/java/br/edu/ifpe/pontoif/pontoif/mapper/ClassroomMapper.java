package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    
    Classroom toEntity(ClassroomDTO classroomDTO);

    ClassroomDTO toDTO(Classroom classroom);

    Classroom fromId(UUID id);
    default UUID toId(Classroom classroom) {
        return classroom != null ? classroom.getId() : null;
    }
}
