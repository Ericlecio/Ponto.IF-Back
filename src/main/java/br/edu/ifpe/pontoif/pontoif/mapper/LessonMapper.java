package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = { DisciplineMapper.class })
public interface LessonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "discipline.id", source = "discipline")
    @Mapping(target = "records", source = "records")
    Lesson toEntity(LessonDTO dto);

    @Mapping(target = "discipline", source = "discipline.id")
    @Mapping(target = "records", source = "records")
    LessonDTO toDTO(Lesson entity);

    default Discipline fromId(UUID id) {
        if (id == null) return null;
        Discipline d = new Discipline();
        d.setId(id);
        return d;
    }

    default UUID toId(Discipline discipline) {
        return discipline != null ? discipline.getId() : null;
    }

    default Record fromRecordId(UUID id) {
        if (id == null) return null;
        Record r = new Record();
        r.setId(id);
        return r;
    }

    default UUID toId(Record record) {
        return record != null ? record.getId() : null;
    }

    default List<UUID> toIdList(List<Record> list) {
        if (list == null) return null;
        return list.stream().map(this::toId).filter(Objects::nonNull).toList();
    }

    default List<Record> fromIdList(List<UUID> list) {
        if (list == null) return null;
        return list.stream().map(this::fromRecordId).toList();
    }
}