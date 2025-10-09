package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
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

@RestController
@RequestMapping("/biometric")
@RequiredArgsConstructor
@Tag(name = "Biometric management", description = "API to manege biometrics")
public class BiometricController {

    private final BiometricService biometricService;

    @Operation(
            summary = "reister a new biometric",
            description = "Endpoint responsible for adding a new biometric",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content (
                                    schema = @Schema(hidden = true)
                            )
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<Void> registerNewBiometric(@Valid @RequestBody BiometricsDTO biometricsDTO) {
        biometricService.insertBiometric(biometricsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete a biometric",
            description = "Deletes biometric by its a ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Biometric deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Biometric not found")
            }
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteBiometric(@RequestParam Long id) {
        boolean deleted = biometricService.deleteBiometric(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Send fingerprint sample",
            description = "Send sample to match",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Biometric match successfully"),
                    @ApiResponse(responseCode = "404", description = "Sample does not match the biometrics in the system")
            }
    )
    @PostMapping("/sample")
    public ResponseEntity<Void> matchSample(@Valid @RequestBody BiometricSampleDTO biometricSampleDTO) {
        return biometricService.matchSample(biometricSampleDTO) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
