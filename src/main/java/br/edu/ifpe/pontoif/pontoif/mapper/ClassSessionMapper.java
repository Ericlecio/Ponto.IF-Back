package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.SessionResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassSessionMapper {
    @Mapping(target = "offeringId", source = "offering.id")
    SessionResponseDTO toDTO(ClassSession classSession);
}
