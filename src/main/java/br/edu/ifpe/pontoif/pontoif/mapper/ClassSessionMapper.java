package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.SessionResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassSessionMapper {
    SessionResponseDTO toDTO(ClassSession classSession);
}
