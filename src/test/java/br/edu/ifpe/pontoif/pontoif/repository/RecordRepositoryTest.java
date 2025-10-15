package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RecordRepositoryTest {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void shouldSaveAndFindRecordById() {
        // Given
        User user = new User();
        user.setName("Ana Costa");
        user.setEmail("ana.costa@example.com");
        user.setRegistration("111111");
        user.setIsActive(true);
        user.setType("STUDENT");
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);

        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(DayOfWeek.MONDAY);
        lesson.setStartTime(LocalTime.of(8, 0));
        lesson.setEndTime(LocalTime.of(10, 0));
        Lesson savedLesson = lessonRepository.save(lesson);

        Record record = new Record();
        record.setUser(savedUser);
        record.setLesson(savedLesson);

        // When
        Record savedRecord = recordRepository.save(record);

        // Then
        assertThat(savedRecord.getId()).isNotNull();
        assertThat(savedRecord.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(savedRecord.getLesson().getId()).isEqualTo(savedLesson.getId());
        assertThat(savedRecord.getDate()).isNotNull();
    }

    @Test
    void shouldFindAllRecordsByUserWithLessons() {
        // Given
        User user = new User();
        user.setName("Carlos Pereira");
        user.setEmail("carlos.pereira@example.com");
        user.setRegistration("222222");
        user.setIsActive(true);
        user.setType("STUDENT");
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);

        Lesson lesson1 = new Lesson();
        lesson1.setDayOfWeek(DayOfWeek.TUESDAY);
        lesson1.setStartTime(LocalTime.of(14, 0));
        lesson1.setEndTime(LocalTime.of(16, 0));
        Lesson savedLesson1 = lessonRepository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setDayOfWeek(DayOfWeek.WEDNESDAY);
        lesson2.setStartTime(LocalTime.of(10, 0));
        lesson2.setEndTime(LocalTime.of(12, 0));
        Lesson savedLesson2 = lessonRepository.save(lesson2);

        Record record1 = new Record();
        record1.setUser(savedUser);
        record1.setLesson(savedLesson1);
        recordRepository.save(record1);

        Record record2 = new Record();
        record2.setUser(savedUser);
        record2.setLesson(savedLesson2);
        recordRepository.save(record2);

        // When
        List<Record> records = recordRepository.findAllByUserWithLessons(savedUser);

        // Then
        assertThat(records).hasSize(2);
        assertThat(records.get(0).getLesson()).isNotNull();
        assertThat(records.get(1).getLesson()).isNotNull();
    }
}
