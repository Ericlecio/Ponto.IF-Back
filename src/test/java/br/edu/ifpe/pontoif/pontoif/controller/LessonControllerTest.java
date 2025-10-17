package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LessonController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.security.config.annotation.web.builders.HttpSecurity.class
        })
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateLesson() throws Exception {
        // Given
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setDayOfWeek(DayOfWeek.MONDAY);
        lessonDTO.setStartTime(LocalTime.of(8, 0));
        lessonDTO.setEndTime(LocalTime.of(10, 0));

        // When & Then
        mockMvc.perform(post("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonDTO)))
                .andExpect(status().isCreated());

        verify(lessonService, times(1)).insertLesson(any(LessonDTO.class));
    }

    @Test
    void shouldGetLessonById() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(id);
        lessonDTO.setDayOfWeek(DayOfWeek.TUESDAY);
        lessonDTO.setStartTime(LocalTime.of(14, 0));
        lessonDTO.setEndTime(LocalTime.of(16, 0));

        when(lessonService.getLessonById(id)).thenReturn(Optional.of(lessonDTO));

        // When & Then
        mockMvc.perform(get("/lesson/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.dayOfWeek").value("TUESDAY"))
                .andExpect(jsonPath("$.startTime").value("14:00:00"))
                .andExpect(jsonPath("$.endTime").value("16:00:00"));
    }

    @Test
    void shouldReturnNotFoundWhenLessonNotExists() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(lessonService.getLessonById(id)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/lesson/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllLessons() throws Exception {
        // Given
        LessonDTO lesson1 = new LessonDTO();
        lesson1.setDayOfWeek(DayOfWeek.MONDAY);
        lesson1.setStartTime(LocalTime.of(8, 0));
        lesson1.setEndTime(LocalTime.of(10, 0));

        LessonDTO lesson2 = new LessonDTO();
        lesson2.setDayOfWeek(DayOfWeek.WEDNESDAY);
        lesson2.setStartTime(LocalTime.of(10, 0));
        lesson2.setEndTime(LocalTime.of(12, 0));

        when(lessonService.getAllLessons()).thenReturn(List.of(lesson1, lesson2));

        // When & Then
        mockMvc.perform(get("/lesson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].dayOfWeek").value("MONDAY"))
                .andExpect(jsonPath("$[1].dayOfWeek").value("WEDNESDAY"));
    }

    @Test
    void shouldGetLessonsByIds() throws Exception {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2);

        LessonDTO lesson1 = new LessonDTO();
        lesson1.setDayOfWeek(DayOfWeek.THURSDAY);
        lesson1.setStartTime(LocalTime.of(16, 0));
        lesson1.setEndTime(LocalTime.of(18, 0));

        LessonDTO lesson2 = new LessonDTO();
        lesson2.setDayOfWeek(DayOfWeek.FRIDAY);
        lesson2.setStartTime(LocalTime.of(18, 0));
        lesson2.setEndTime(LocalTime.of(20, 0));

        when(lessonService.getLessonsByIds(ids)).thenReturn(List.of(lesson1, lesson2));

        // When & Then
        mockMvc.perform(get("/lesson/by-ids")
                        .param("ids", id1.toString(), id2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateLesson() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
        LessonDTO updateDTO = new LessonDTO();
        updateDTO.setDayOfWeek(DayOfWeek.FRIDAY);
        updateDTO.setStartTime(LocalTime.of(16, 0));
        updateDTO.setEndTime(LocalTime.of(18, 0));

        LessonDTO updatedLesson = new LessonDTO();
        updatedLesson.setId(id);
        updatedLesson.setDayOfWeek(DayOfWeek.FRIDAY);
        updatedLesson.setStartTime(LocalTime.of(16, 0));
        updatedLesson.setEndTime(LocalTime.of(18, 0));

        when(lessonService.updateLesson(eq(id), any(LessonDTO.class))).thenReturn(Optional.of(updatedLesson));

        // When & Then
        mockMvc.perform(put("/lesson/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.dayOfWeek").value("FRIDAY"))
                .andExpect(jsonPath("$.startTime").value("16:00:00"))
                .andExpect(jsonPath("$.endTime").value("18:00:00"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentLesson() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        LessonDTO updateDTO = new LessonDTO();
        updateDTO.setDayOfWeek(DayOfWeek.SATURDAY);
        updateDTO.setStartTime(LocalTime.of(9, 0));
        updateDTO.setEndTime(LocalTime.of(11, 0));

        when(lessonService.updateLesson(id, updateDTO)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/lesson/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteLesson() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(lessonService.deleteLesson(id)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/lesson/{id}", id))
                .andExpect(status().isNoContent());

        verify(lessonService, times(1)).deleteLesson(id);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentLesson() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(lessonService.deleteLesson(id)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/lesson/{id}", id))
                .andExpect(status().isNotFound());

        verify(lessonService, times(1)).deleteLesson(id);
    }
}
