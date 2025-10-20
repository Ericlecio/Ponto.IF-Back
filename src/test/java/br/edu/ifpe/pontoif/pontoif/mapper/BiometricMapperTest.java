package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class BiometricMapperTest {

    private final BiometricMapper biometricMapper = Mappers.getMapper(BiometricMapper.class);

    @Test
    void shouldMapBiometricsDTOToEntity() {
        // Given
        BiometricsDTO dto = new BiometricsDTO();
        dto.setId(123456789L);

        // When
        Biometric entity = biometricMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(123456789L);
    }

    @Test
    void shouldMapBiometricEntityToDTO() {
        // Given
        Biometric entity = new Biometric();
        entity.setId(987654321L);

        // When
        BiometricsDTO dto = biometricMapper.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(987654321L);
    }
}
