package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.service.ClassroomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClassroomController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.security.config.annotation.web.builders.HttpSecurity.class
        })
class ClassroomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassroomService classroomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateClassroom() throws Exception {
        // Given
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setCode("CC-2024-1");

        // When & Then
        mockMvc.perform(post("/classroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classroomDTO)))
                .andExpect(status().isCreated());

        verify(classroomService, times(1)).insertClassroom(any(ClassroomDTO.class));
    }

    @Test
    void shouldGetClassroomById() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440008");
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setId(id);
        classroomDTO.setCode("SI-2024-2");

        when(classroomService.getClassroomById(id)).thenReturn(Optional.of(classroomDTO));

        // When & Then
        mockMvc.perform(get("/classroom/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.code").value("SI-2024-2"));
    }

    @Test
    void shouldReturnNotFoundWhenClassroomNotExists() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440009");
        when(classroomService.getClassroomById(id)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/classroom/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllClassrooms() throws Exception {
        // Given
        ClassroomDTO classroom1 = new ClassroomDTO();
        classroom1.setCode("CC-2024-1");

        ClassroomDTO classroom2 = new ClassroomDTO();
        classroom2.setCode("ES-2024-1");

        when(classroomService.getAllClassrooms()).thenReturn(List.of(classroom1, classroom2));

        // When & Then
        mockMvc.perform(get("/classroom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].code").value("CC-2024-1"))
                .andExpect(jsonPath("$[1].code").value("ES-2024-1"));
    }

    @Test
    void shouldDeleteClassroom() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440010");
        when(classroomService.deleteClassroom(id)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/classroom/{id}", id))
                .andExpect(status().isNoContent());

        verify(classroomService, times(1)).deleteClassroom(id);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentClassroom() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440011");
        when(classroomService.deleteClassroom(id)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/classroom/{id}", id))
                .andExpect(status().isNotFound());

        verify(classroomService, times(1)).deleteClassroom(id);
    }
}
