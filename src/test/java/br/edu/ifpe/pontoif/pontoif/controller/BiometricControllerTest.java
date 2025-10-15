package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.service.BiometricService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BiometricController.class)
class BiometricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BiometricService biometricService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Profile("dev")
    void shouldRegisterNewBiometric() throws Exception {
        // Given
        BiometricsDTO biometricsDTO = new BiometricsDTO();
        biometricsDTO.setId(123456789L);

        // When & Then
        mockMvc.perform(post("/biometric")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(biometricsDTO)))
                .andExpect(status().isCreated());

        verify(biometricService, times(1)).insertBiometric(any(BiometricsDTO.class));
    }

    @Test
    @Profile("dev")
    void shouldDeleteBiometric() throws Exception {
        // Given
        Long id = 987654321L;
        when(biometricService.deleteBiometric(id)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/biometric")
                        .param("id", id.toString()))
                .andExpect(status().isNoContent());

        verify(biometricService, times(1)).deleteBiometric(id);
    }

    @Test
    @Profile("dev")
    void shouldReturnNotFoundWhenDeletingNonExistentBiometric() throws Exception {
        // Given
        Long id = 111111111L;
        when(biometricService.deleteBiometric(id)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/biometric")
                        .param("id", id.toString()))
                .andExpect(status().isNotFound());

        verify(biometricService, times(1)).deleteBiometric(id);
    }

    @Test
    @Profile("dev")
    void shouldMatchSampleSuccessfully() throws Exception {
        // Given
        BiometricSampleDTO sampleDTO = new BiometricSampleDTO();
        sampleDTO.setId(222222222L);

        Biometric biometric = new Biometric();
        biometric.setId(222222222L);

        when(biometricService.matchSample(sampleDTO)).thenReturn(Optional.of(biometric));

        // When & Then
        mockMvc.perform(post("/biometric/sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDTO)))
                .andExpect(status().isOk());

        verify(biometricService, times(1)).matchSample(any(BiometricSampleDTO.class));
    }

    @Test
    void shouldReturnNotFoundWhenSampleDoesNotMatch() throws Exception {
        // Given
        BiometricSampleDTO sampleDTO = new BiometricSampleDTO();
        sampleDTO.setId(333333333L);

        when(biometricService.matchSample(sampleDTO)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/biometric/sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDTO)))
                .andExpect(status().isNotFound());

        verify(biometricService, times(1)).matchSample(any(BiometricSampleDTO.class));
    }
}
