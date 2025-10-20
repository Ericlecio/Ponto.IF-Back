package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import br.edu.ifpe.pontoif.pontoif.service.BiometricService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BiometricController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.security.config.annotation.web.builders.HttpSecurity.class
        })
class BiometricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
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
                        .with(csrf())
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
                        .with(csrf())
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
                        .with(csrf())
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
        var user = User.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440007"))
                .name("Test User")
                .email("teste@email.com")
                .role(Role.STUDENT)
                .build();
        biometric.setUser(user);
        biometric.setRecords(new ArrayList<>());
        biometric.setCreatedAt(LocalDateTime.now());

        when(biometricService.matchSample(any(BiometricSampleDTO.class))).thenReturn(Optional.of(biometric));

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

    @Test
    void shouldGetBiometricById() throws Exception {
        // Given
        Long id = 123456789L;
        BiometricsDTO biometricsDTO = new BiometricsDTO();
        biometricsDTO.setId(id);
        biometricsDTO.setUser(UUID.fromString("550e8400-e29b-41d4-a716-446655440008"));

        when(biometricService.getBiometricById(id)).thenReturn(Optional.of(biometricsDTO));

        // When & Then
        mockMvc.perform(get("/biometric/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.user").value("550e8400-e29b-41d4-a716-446655440008"));

        verify(biometricService, times(1)).getBiometricById(id);
    }
}
