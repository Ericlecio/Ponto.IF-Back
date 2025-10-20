package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import br.edu.ifpe.pontoif.pontoif.service.RecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecordController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.security.config.annotation.web.builders.HttpSecurity.class
        })
class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordService recordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRecord() throws Exception {
        // Given
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setDate(LocalDateTime.now());

        // When & Then
        mockMvc.perform(post("/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated());

        verify(recordService, times(1)).insertRecord(any(RecordDTO.class));
    }

    @Test
    void shouldGetRecordById() throws Exception {
        // Given
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440020");
        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setId(id);
        recordDTO.setDate(LocalDateTime.now());
        recordDTO.setUser(UUID.fromString("550e8400-e29b-41d4-a716-446655440021"));
        recordDTO.setLesson(UUID.fromString("550e8400-e29b-41d4-a716-446655440022"));

        when(recordService.getRecordById(id)).thenReturn(Optional.of(recordDTO));

        // When & Then
        mockMvc.perform(get("/record/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.user").value("550e8400-e29b-41d4-a716-446655440021"))
                .andExpect(jsonPath("$.lesson").value("550e8400-e29b-41d4-a716-446655440022"));
    }

    @Test
    void shouldReturnNotFoundWhenRecordNotExists() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(recordService.getRecordById(id)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/record/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllRecords() throws Exception {
        // Given
        RecordDTO record1 = new RecordDTO();
        record1.setDate(LocalDateTime.now().minusDays(1));

        RecordDTO record2 = new RecordDTO();
        record2.setDate(LocalDateTime.now());

        when(recordService.getAllRecords()).thenReturn(List.of(record1, record2));

        // When & Then
        mockMvc.perform(get("/record"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldGetRecordsByIds() throws Exception {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2);

        RecordDTO record1 = new RecordDTO();
        record1.setDate(LocalDateTime.now());

        RecordDTO record2 = new RecordDTO();
        record2.setDate(LocalDateTime.now().minusHours(1));

        when(recordService.getRecordsByIds(ids)).thenReturn(List.of(record1, record2));

        // When & Then
        mockMvc.perform(get("/record/by-ids")
                        .param("ids", id1.toString(), id2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateRecord() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        RecordDTO updateDTO = new RecordDTO();
        updateDTO.setDate(LocalDateTime.now());

        RecordDTO updatedRecord = new RecordDTO();
        updatedRecord.setDate(LocalDateTime.now());

        when(recordService.updateRecord(eq(id), any(RecordDTO.class))).thenReturn(Optional.of(updatedRecord));

        // When & Then
        mockMvc.perform(put("/record/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentRecord() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        RecordDTO updateDTO = new RecordDTO();
        updateDTO.setDate(LocalDateTime.now());

        when(recordService.updateRecord(id, updateDTO)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/record/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteRecord() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(recordService.deleteRecord(id)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/record/{id}", id))
                .andExpect(status().isNoContent());

        verify(recordService, times(1)).deleteRecord(id);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentRecord() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(recordService.deleteRecord(id)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/record/{id}", id))
                .andExpect(status().isNotFound());

        verify(recordService, times(1)).deleteRecord(id);
    }
}
