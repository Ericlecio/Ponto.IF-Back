package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BiometricMapper {

    @Mapping(target = "user.id", source = "user")
    Biometric toEntity(BiometricsDTO biometricsDTO);

    @Mapping(target = "user", source = "user.id")
    BiometricsDTO toDTO(Biometric biometric);

    Biometric fromId(Long id);
    default Long toId(Biometric biometric) {
        return biometric != null ? biometric.getId() : null;
    }
}
