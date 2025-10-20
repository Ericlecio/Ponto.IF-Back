package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import br.edu.ifpe.pontoif.pontoif.mapper.LessonMapper;
import br.edu.ifpe.pontoif.pontoif.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private RecordService recordService;

    @InjectMocks
    private LessonService lessonService;

    @Test
    void shouldInsertLesson() {
        // Given
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setDayOfWeek(DayOfWeek.MONDAY);
        lessonDTO.setStartTime(LocalTime.of(8, 0));
        lessonDTO.setEndTime(LocalTime.of(10, 0));

        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(DayOfWeek.MONDAY);
        lesson.setStartTime(LocalTime.of(8, 0));
        lesson.setEndTime(LocalTime.of(10, 0));

        when(lessonMapper.toEntity(lessonDTO)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);

        // When
        lessonService.insertLesson(lessonDTO);

        // Then
        verify(lessonRepository, times(1)).save(lesson);
        verify(lessonMapper, times(1)).toEntity(lessonDTO);
    }

    @Test
    void shouldGetLessonById() {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setDayOfWeek(DayOfWeek.TUESDAY);
        lesson.setStartTime(LocalTime.of(14, 0));
        lesson.setEndTime(LocalTime.of(16, 0));

        LessonDTO dto = new LessonDTO();
        dto.setDayOfWeek(DayOfWeek.TUESDAY);
        dto.setStartTime(LocalTime.of(14, 0));
        dto.setEndTime(LocalTime.of(16, 0));

        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(lessonMapper.toDTO(lesson)).thenReturn(dto);

        // When
        Optional<LessonDTO> result = lessonService.getLessonById(id);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY);
    }

    @Test
    void shouldGetAllLessons() {
        // Given
        Lesson lesson1 = new Lesson();
        lesson1.setDayOfWeek(DayOfWeek.MONDAY);
        lesson1.setStartTime(LocalTime.of(8, 0));
        lesson1.setEndTime(LocalTime.of(10, 0));

        Lesson lesson2 = new Lesson();
        lesson2.setDayOfWeek(DayOfWeek.WEDNESDAY);
        lesson2.setStartTime(LocalTime.of(10, 0));
        lesson2.setEndTime(LocalTime.of(12, 0));

        LessonDTO dto1 = new LessonDTO();
        dto1.setDayOfWeek(DayOfWeek.MONDAY);
        dto1.setStartTime(LocalTime.of(8, 0));
        dto1.setEndTime(LocalTime.of(10, 0));

        LessonDTO dto2 = new LessonDTO();
        dto2.setDayOfWeek(DayOfWeek.WEDNESDAY);
        dto2.setStartTime(LocalTime.of(10, 0));
        dto2.setEndTime(LocalTime.of(12, 0));

        when(lessonRepository.findAll()).thenReturn(List.of(lesson1, lesson2));
        when(lessonMapper.toDTO(lesson1)).thenReturn(dto1);
        when(lessonMapper.toDTO(lesson2)).thenReturn(dto2);

        // When
        List<LessonDTO> result = lessonService.getAllLessons();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(result.get(1).getDayOfWeek()).isEqualTo(DayOfWeek.WEDNESDAY);
    }

    @Test
    void shouldUpdateLesson() {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        Lesson existing = new Lesson();
        existing.setId(id);
        existing.setDayOfWeek(DayOfWeek.MONDAY);
        existing.setStartTime(LocalTime.of(8, 0));
        existing.setEndTime(LocalTime.of(10, 0));

        LessonDTO updateDTO = new LessonDTO();
        updateDTO.setDayOfWeek(DayOfWeek.FRIDAY);
        updateDTO.setStartTime(LocalTime.of(16, 0));
        updateDTO.setEndTime(LocalTime.of(18, 0));

        Lesson updated = new Lesson();
        updated.setId(id);
        updated.setDayOfWeek(DayOfWeek.FRIDAY);
        updated.setStartTime(LocalTime.of(16, 0));
        updated.setEndTime(LocalTime.of(18, 0));

        LessonDTO resultDTO = new LessonDTO();
        resultDTO.setDayOfWeek(DayOfWeek.FRIDAY);
        resultDTO.setStartTime(LocalTime.of(16, 0));
        resultDTO.setEndTime(LocalTime.of(18, 0));

        when(lessonRepository.findById(id)).thenReturn(Optional.of(existing));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(updated);
        when(lessonMapper.toDTO(updated)).thenReturn(resultDTO);

        // When
        Optional<LessonDTO> result = lessonService.updateLesson(id, updateDTO);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getDayOfWeek()).isEqualTo(DayOfWeek.FRIDAY);
        assertThat(result.get().getStartTime()).isEqualTo(LocalTime.of(16, 0));
    }

    @Test
    void shouldDeleteLesson() {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setDayOfWeek(DayOfWeek.THURSDAY);

        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));

        // When
        boolean result = lessonService.deleteLesson(id);

        // Then
        assertThat(result).isTrue();
        verify(lessonRepository, times(1)).delete(lesson);
    }

    @Test
    void shouldGetCurrentLesson() {
        // Given
        User user = new User();
        user.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"));
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRegistration("12345");
        user.setIsActive(true);
        user.setType("STUDENT");
        user.setRole(Role.STUDENT);

        Lesson lesson = new Lesson();
        lesson.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"));
        lesson.setDayOfWeek(DayOfWeek.MONDAY);
        lesson.setStartTime(LocalTime.of(8, 0));
        lesson.setEndTime(LocalTime.of(10, 0));

        Record record = new Record();
        record.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440006"));
        record.setLesson(lesson);

        when(recordService.getRecordsByUser(user)).thenReturn(List.of(record));
        when(lessonRepository.findIfActive(any(UUID.class), any(DayOfWeek.class), any(LocalTime.class)))
                .thenReturn(Optional.of(lesson));

        // When
        Optional<Lesson> result = lessonService.getCurrentLesson(user);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
    }
}
