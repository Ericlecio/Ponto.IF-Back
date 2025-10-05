package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BiometricMapper {

    @Mapping(target = "id", ignore = true)
    Biometric toEntity(BiometricsDTO biometricsDTO);

    BiometricsDTO toDTO(Biometric biometric);
}
