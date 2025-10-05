package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    Classroom toEntity(ClassroomDTO classroomDTO);

    ClassroomDTO toDTO(Classroom classroom);
}
