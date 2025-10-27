package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface BiometricMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "user")
    @Mapping(target = "template", ignore = true)
    Biometric toEntity(BiometricDTO dto);

    @Mapping(target = "user", source = "user.id")
    BiometricDTO toDTO(Biometric entity);

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
