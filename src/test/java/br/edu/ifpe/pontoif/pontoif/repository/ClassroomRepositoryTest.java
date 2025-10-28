package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ClassroomRepositoryTest {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldSaveAndFindClassroomById() {
        // Given
        Course course = new Course();
        course.setName("Ciência da Computação");
        course.setAcronym("CC");
        course.setDurationInMonths(48);
        course.setStartTime(LocalDateTime.now());
        course.setEndTime(LocalDateTime.now().plusMonths(48));
        Course savedCourse = courseRepository.save(course);

        Classroom classroom = new Classroom();
        classroom.setCode("CC-2024-1");
        classroom.setCourse(savedCourse);

        // When
        Classroom savedClassroom = classroomRepository.save(classroom);

        // Then
        assertThat(savedClassroom.getId()).isNotNull();
        assertThat(savedClassroom.getCode()).isEqualTo("CC-2024-1");
        assertThat(savedClassroom.getCourse().getId()).isEqualTo(savedCourse.getId());
    }

    @Test
    void shouldSaveClassroomWithoutCourse() {
        // Given
        Classroom classroom = new Classroom();
        classroom.setCode("TURMA-GERAL");

        // When
        Classroom savedClassroom = classroomRepository.save(classroom);

        // Then
        assertThat(savedClassroom.getId()).isNotNull();
        assertThat(savedClassroom.getCode()).isEqualTo("TURMA-GERAL");
        assertThat(savedClassroom.getCourse()).isNull();
    }
}
