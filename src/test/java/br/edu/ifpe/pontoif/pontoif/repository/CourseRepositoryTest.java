package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldSaveAndFindCourseById() {
        // Given
        Course course = new Course();
        course.setName("Ciência da Computação");
        course.setAcronym("CC");
        course.setDurationInMonths(48);
        course.setStartTime(LocalDateTime.now());
        course.setEndTime(LocalDateTime.now().plusMonths(48));

        // When
        Course savedCourse = courseRepository.save(course);

        // Then
        assertThat(savedCourse.getId()).isNotNull();
        assertThat(savedCourse.getName()).isEqualTo("Ciência da Computação");
        assertThat(savedCourse.getAcronym()).isEqualTo("CC");
    }

    @Test
    void shouldFindCourseByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();
        Course course = new Course();
        course.setCorrelationId(correlationId);
        course.setName("Engenharia de Software");
        course.setAcronym("ES");
        course.setDurationInMonths(60);
        course.setStartTime(LocalDateTime.now());
        course.setEndTime(LocalDateTime.now().plusMonths(60));
        courseRepository.save(course);

        // When
        Course foundCourse = courseRepository.findByCorrelationId(correlationId);

        // Then
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getCorrelationId()).isEqualTo(correlationId);
        assertThat(foundCourse.getName()).isEqualTo("Engenharia de Software");
    }

    @Test
    void shouldReturnTrueIfCourseExistsByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();
        Course course = new Course();
        course.setCorrelationId(correlationId);
        course.setName("Sistemas de Informação");
        course.setAcronym("SI");
        course.setDurationInMonths(42);
        course.setStartTime(LocalDateTime.now());
        course.setEndTime(LocalDateTime.now().plusMonths(42));
        courseRepository.save(course);

        // When
        boolean exists = courseRepository.existsByCorrelationId(correlationId);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseIfCourseDoesNotExistByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();

        // When
        boolean exists = courseRepository.existsByCorrelationId(correlationId);

        // Then
        assertThat(exists).isFalse();
    }
}
