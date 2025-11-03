package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "biometrics", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "correlationId", source = "id")
    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);

    default User fromId(UUID id) {
        if (id == null) return null;
        User u = new User();
        u.setId(id);
        return u;
    }

    default UUID toId(User user) {
        return user != null ? user.getId() : null;
    }

    default List<UUID> toIdList(List<User> list) {
        if (list == null) return null;
        return list.stream().map(this::toId).filter(Objects::nonNull).toList();
    }

    default List<User> fromIdList(List<UUID> list) {
        if (list == null) return null;
        return list.stream().map(this::fromId).toList();
    }
}
