package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "session", source = "sessionId")
    @Mapping(target = "student", source = "studentId")
    AttendanceRecord toEntity(AttendanceDTO dto);

    @Mapping(target = "sessionId", source = "session.id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "offeringId", source = "session.offering.id")
    AttendanceDTO toDTO(AttendanceRecord record);

    List<AttendanceDTO> toDTOList(List<AttendanceRecord> records);

    default ClassSession fromId(Long id) {
        if (id == null) return null;
        ClassSession session = new ClassSession();
        session.setId(id);
        return session;
    }

    default Long toId(ClassSession session) {
        return session != null ? session.getId() : null;
    }

    default User fromId(UUID id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

    default UUID toId(User user) {
        return user != null ? user.getId() : null;
    }
}