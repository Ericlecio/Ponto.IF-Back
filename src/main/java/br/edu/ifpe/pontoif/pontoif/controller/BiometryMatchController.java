package br.edu.ifpe.pontoif.pontoif.controller;


import br.edu.ifpe.pontoif.pontoif.dto.BiometricMatchResultDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.service.BiometricService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/biometric")
@RequiredArgsConstructor
public class BiometryMatchController {

    private final BiometricService biometricService;

    @Operation(summary = "Match a fingerprint sample and return scored results")
    @PostMapping("/match")
    public ResponseEntity<Optional<BiometricMatchResultDTO>> match(
            @Valid @RequestBody BiometricSampleDTO sample) {

        Optional<BiometricMatchResultDTO> matche = biometricService.matchSample(sample);
        return ResponseEntity.ok(matche);
    }
}