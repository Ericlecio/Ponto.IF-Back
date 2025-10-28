package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void shouldSaveAndFindLessonById() {
        // Given
        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(DayOfWeek.MONDAY);
        lesson.setStartTime(LocalTime.of(8, 0));
        lesson.setEndTime(LocalTime.of(10, 0));

        // When
        Lesson savedLesson = lessonRepository.save(lesson);

        // Then
        assertThat(savedLesson.getId()).isNotNull();
        assertThat(savedLesson.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(savedLesson.getStartTime()).isEqualTo(LocalTime.of(8, 0));
    }

    @Test
    void shouldFindActiveLessonWhenTimeIsWithinRange() {
        // Given
        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(DayOfWeek.TUESDAY);
        lesson.setStartTime(LocalTime.of(14, 0));
        lesson.setEndTime(LocalTime.of(16, 0));
        Lesson savedLesson = lessonRepository.save(lesson);

        // When
        Optional<Lesson> activeLesson = lessonRepository.findIfActive(
                savedLesson.getId(),
                DayOfWeek.TUESDAY,
                LocalTime.of(15, 0) // Within range
        );

        // Then
        assertThat(activeLesson).isPresent();
        assertThat(activeLesson.get().getId()).isEqualTo(savedLesson.getId());
    }

    @Test
    void shouldNotFindActiveLessonWhenTimeIsOutsideRange() {
        // Given
        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(DayOfWeek.WEDNESDAY);
        lesson.setStartTime(LocalTime.of(10, 0));
        lesson.setEndTime(LocalTime.of(12, 0));
        Lesson savedLesson = lessonRepository.save(lesson);

        // When
        Optional<Lesson> activeLesson = lessonRepository.findIfActive(
                savedLesson.getId(),
                DayOfWeek.WEDNESDAY,
                LocalTime.of(13, 0) // Outside range
        );

        // Then
        assertThat(activeLesson).isEmpty();
    }

    @Test
    void shouldNotFindActiveLessonWhenDayDoesNotMatch() {
        // Given
        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(DayOfWeek.THURSDAY);
        lesson.setStartTime(LocalTime.of(16, 0));
        lesson.setEndTime(LocalTime.of(18, 0));
        Lesson savedLesson = lessonRepository.save(lesson);

        // When
        Optional<Lesson> activeLesson = lessonRepository.findIfActive(
                savedLesson.getId(),
                DayOfWeek.FRIDAY, // Different day
                LocalTime.of(17, 0)
        );

        // Then
        assertThat(activeLesson).isEmpty();
    }
}
