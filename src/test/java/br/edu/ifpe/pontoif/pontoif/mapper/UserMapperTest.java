package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserDTOToEntity() {
        // Given
        UserDTO dto = new UserDTO();
        dto.setId(java.util.UUID.randomUUID());

        // When
        User entity = userMapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getCorrelationId()).isEqualTo(dto.getId());
        assertThat(entity.getId()).isNull(); // ignored
        assertThat(entity.getType()).isNull(); // ignored
        assertThat(entity.getIsActive()).isNull(); // ignored
        assertThat(entity.getBiometrics()).isNull(); // ignored
    }
}
