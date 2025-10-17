package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "correlationId", source = "id")
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "biometrics", ignore = true)
    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);

    User fromId(UUID id);
    default UUID toId(User user) {
        return user != null ? user.getId() : null;
    }
}
