package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricDTO;
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
import java.util.UUID;

@RestController
@RequestMapping("/biometric")
@RequiredArgsConstructor
@Tag(name = "Biometric management", description = "API to manage biometrics")
public class BiometricController {

    private final BiometricService biometricService;

    @Operation(
            summary = "Enroll  a new biometric",
            description = "Endpoint responsible for adding a new biometric",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/enroll")
    public ResponseEntity<Void> enrollBiometric(@Valid @RequestBody BiometricDTO biometricDTO) {
        biometricService.insertBiometric(biometricDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get biometric by ID",
            description = "Retrieve a biometric by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Biometric found"),
                    @ApiResponse(responseCode = "404", description = "Biometric not found")
            }
    )
    @GetMapping("/fingerprint/{id}")
    public ResponseEntity<BiometricDTO> getBiometricById(@PathVariable UUID id) {
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
    @GetMapping("/fingerprint")
    public ResponseEntity<List<BiometricDTO>> getAllBiometrics() {
        List<BiometricDTO> biometrics = biometricService.getAllBiometrics();
        return ResponseEntity.ok(biometrics);
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
    public ResponseEntity<Void> deleteBiometric(@PathVariable UUID id) {
        return biometricService.deleteBiometric(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
