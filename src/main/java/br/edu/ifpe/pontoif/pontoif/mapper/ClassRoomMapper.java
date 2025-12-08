package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.ClassRoomRequestDTO;
import br.edu.ifpe.pontoif.pontoif.dto.ClassRoomResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassRoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Classroom toEntity(ClassRoomRequestDTO dto);

    ClassRoomResponseDTO toDTO(Classroom entity);

    List<ClassRoomResponseDTO> toDTOs(List<Classroom> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(ClassRoomRequestDTO dto, @MappingTarget Classroom entity);
}
