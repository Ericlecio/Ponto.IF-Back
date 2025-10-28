package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = { LessonMapper.class, UserMapper.class })
public interface RecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson.id", source = "lesson")
    @Mapping(target = "user.id", source = "user")
    Record toEntity(RecordDTO dto);

    @Mapping(target = "lesson", source = "lesson.id")
    @Mapping(target = "user", source = "user.id")
    RecordDTO toDTO(Record entity);

    default Lesson fromLessonId(UUID id) {
        if (id == null) return null;
        Lesson l = new Lesson();
        l.setId(id);
        return l;
    }

    default UUID toId(Lesson lesson) {
        return lesson != null ? lesson.getId() : null;
    }

    default User fromUserId(UUID id) {
        if (id == null) return null;
        User u = new User();
        u.setId(id);
        return u;
    }

    default UUID toId(User user) {
        return user != null ? user.getId() : null;
    }

    default List<UUID> toLessonIdList(List<Lesson> list) {
        if (list == null) return null;
        return list.stream().map(this::toId).filter(Objects::nonNull).toList();
    }

    default List<Lesson> fromLessonIdList(List<UUID> list) {
        if (list == null) return null;
        return list.stream().map(this::fromLessonId).toList();
    }
}