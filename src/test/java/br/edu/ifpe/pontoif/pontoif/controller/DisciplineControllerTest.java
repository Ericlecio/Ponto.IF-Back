package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.service.DisciplineService;
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

@WebMvcTest(DisciplineController.class)
class DisciplineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisciplineService disciplineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDiscipline() throws Exception {
        // Given
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setName("Algoritmos");
        disciplineDTO.setWorkload(80);

        // When & Then
        mockMvc.perform(post("/discipline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplineDTO)))
                .andExpect(status().isCreated());

        verify(disciplineService, times(1)).insertDiscipline(any(DisciplineDTO.class));
    }

    @Test
    void shouldGetDisciplineById() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setName("Estrutura de Dados");
        disciplineDTO.setWorkload(70);

        when(disciplineService.getDisciplineById(id)).thenReturn(Optional.of(disciplineDTO));

        // When & Then
        mockMvc.perform(get("/discipline/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Estrutura de Dados"))
                .andExpect(jsonPath("$.workload").value(70));
    }

    @Test
    void shouldReturnNotFoundWhenDisciplineNotExists() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(disciplineService.getDisciplineById(id)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/discipline/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllDisciplines() throws Exception {
        // Given
        DisciplineDTO discipline1 = new DisciplineDTO();
        discipline1.setName("Programação I");
        discipline1.setWorkload(60);

        DisciplineDTO discipline2 = new DisciplineDTO();
        discipline2.setName("Banco de Dados");
        discipline2.setWorkload(80);

        when(disciplineService.getAllDisciplines()).thenReturn(List.of(discipline1, discipline2));

        // When & Then
        mockMvc.perform(get("/discipline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Programação I"))
                .andExpect(jsonPath("$[1].name").value("Banco de Dados"));
    }

    @Test
    void shouldGetDisciplinesByIds() throws Exception {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<UUID> ids = List.of(id1, id2);

        DisciplineDTO discipline1 = new DisciplineDTO();
        discipline1.setName("Redes de Computadores");
        discipline1.setWorkload(60);

        DisciplineDTO discipline2 = new DisciplineDTO();
        discipline2.setName("Sistemas Operacionais");
        discipline2.setWorkload(70);

        when(disciplineService.getDisciplinesByIds(ids)).thenReturn(List.of(discipline1, discipline2));

        // When & Then
        mockMvc.perform(get("/discipline/by-ids")
                        .param("ids", id1.toString(), id2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateDiscipline() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        DisciplineDTO updateDTO = new DisciplineDTO();
        updateDTO.setName("Programação II");
        updateDTO.setWorkload(80);

        DisciplineDTO updatedDiscipline = new DisciplineDTO();
        updatedDiscipline.setName("Programação II");
        updatedDiscipline.setWorkload(80);

        when(disciplineService.updateDiscipline(id, updateDTO)).thenReturn(Optional.of(updatedDiscipline));

        // When & Then
        mockMvc.perform(put("/discipline/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Programação II"))
                .andExpect(jsonPath("$.workload").value(80));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentDiscipline() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        DisciplineDTO updateDTO = new DisciplineDTO();
        updateDTO.setName("Disciplina Atualizada");
        updateDTO.setWorkload(90);

        when(disciplineService.updateDiscipline(id, updateDTO)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/discipline/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteDiscipline() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(disciplineService.deleteDiscipline(id)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/discipline/{id}", id))
                .andExpect(status().isNoContent());

        verify(disciplineService, times(1)).deleteDiscipline(id);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentDiscipline() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        when(disciplineService.deleteDiscipline(id)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/discipline/{id}", id))
                .andExpect(status().isNotFound());

        verify(disciplineService, times(1)).deleteDiscipline(id);
    }
}
