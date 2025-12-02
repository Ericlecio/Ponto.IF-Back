package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.dto.TokenDTO;
import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.service.BiometricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biometric")
@RequiredArgsConstructor
@Tag(name = "Biometric management", description = "API to manage biometrics")
public class BiometricController {

    private final BiometricService biometricService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Register a new biometric",
            description = "Endpoint responsible for adding a new biometric",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping
    public ResponseEntity<UserDTO> createBiometric(@Valid @RequestBody BiometricDTO biometricDTO) {
        var match = biometricService.matchSample(biometricSampleDTO);

        if (match.isPresent()) {
            // Converte o usuário encontrado para DTO e retorna no corpo da resposta
            UserDTO userDTO = userMapper.toDTO(match.get().getUser());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get biometric by ID",
            description = "Retrieve a biometric by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Biometric found"),
                    @ApiResponse(responseCode = "404", description = "Biometric not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BiometricDTO> getBiometricById(@PathVariable Long id) {
        Optional<BiometricDTO> biometric = biometricService.getBiometricById(id);
        return biometric.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "List all biometrics",
            description = "Retrieve all biometrics from the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<BiometricDTO>> getAllBiometrics() {
        List<BiometricDTO> biometrics = biometricService.getAllBiometrics();
        return ResponseEntity.ok(biometrics);
    }

    @Operation(
            summary = "Send fingerprint sample",
            description = "Send a fingerprint sample to match against registered biometrics",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Biometric matched successfully"),
                    @ApiResponse(responseCode = "404", description = "No biometric match found")
            }
    )
    @PostMapping("/sample")
    public ResponseEntity<Void> matchSample(@Valid @RequestBody BiometricSampleDTO biometricSampleDTO) {
        return biometricService.matchSample(biometricSampleDTO).isPresent()
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> matchAuth(@Valid @RequestBody BiometricSampleDTO biometricSampleDTO) {
        return biometricService.matchAuth(biometricSampleDTO).isPresent()
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Delete a biometric",
            description = "Deletes a biometric by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Biometric not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBiometric(@PathVariable Long id) {
        return biometricService.deleteBiometric(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
