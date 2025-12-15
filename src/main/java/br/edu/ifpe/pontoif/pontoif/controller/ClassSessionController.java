package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.SessionResponseDTO;
import br.edu.ifpe.pontoif.pontoif.service.ClassSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/class-session")
@RequiredArgsConstructor
@Tag(name = "ClassSession", description = "API for session of class management")
public class ClassSessionController {

    private final ClassSessionService service;

    @Operation(summary = "get actual session by offer id")
    @GetMapping("/{offerId}")
    public ResponseEntity<SessionResponseDTO> getByOfferId(@Valid @PathVariable Long offerId) {
        return ResponseEntity.ok(service.getActualSessionId(offerId));
    }

    @Operation(summary = "Get all active session")
    @GetMapping("/allActive")
    public ResponseEntity<List<SessionResponseDTO>> getAllActive(){
        return ResponseEntity.ok(service.getAllActiveSessions());
    }

    @Operation(summary = "Get all session by subject id")
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<SessionResponseDTO>> getAllBySubject(@PathVariable UUID subjectId){
        return ResponseEntity.ok(service.getAllBySubject(subjectId));
    }
}