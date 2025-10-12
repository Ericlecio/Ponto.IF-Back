package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DisciplineMapper {

    @Mapping(target = "classroom.id", source = "classroom")
    @Mapping(target = "lessons", source = "lessons")
    Discipline toEntity(DisciplineDTO dto);

    @Mapping(target = "classroom", source = "classroom.id")
    @Mapping(target = "lessons", source = "lessons")
    DisciplineDTO toDTO(Discipline entity);

    Discipline fromId(java.util.UUID id);
    java.util.UUID toId(Discipline discipline);
}
