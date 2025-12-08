package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.SubjectOfferingDTO;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SubjectOfferingMapper {

    @Mapping(target = "courseId", source = "courseSubject.course.id")
    @Mapping(target = "subjectId", source = "courseSubject.subject.id")
    @Mapping(source = "classroom.id", target = "classroomId")
    @Mapping(source = "teacher.id", target = "teacherId")
    SubjectOfferingDTO toDTO(SubjectOffering entity);

    @Mapping(target = "courseSubject", ignore = true)
    @Mapping(source = "classroomId", target = "classroom")
    @Mapping(source = "teacherId", target = "teacher")
    SubjectOffering toEntity(SubjectOfferingDTO dto);

    default CourseSubject mapCourseSubject(Long id) {
        if (id == null) return null;
        CourseSubject cs = new CourseSubject();
        cs.setId(id);
        return cs;
    }

    default Classroom mapClassroom(Long id) {
        if (id == null) return null;
        Classroom c = new Classroom();
        c.setId(id);
        return c;
    }

    default User mapUser(String id) {
        if (id == null) return null;
        User u = new User();
        u.setId(java.util.UUID.fromString(id));
        return u;
    }
}
