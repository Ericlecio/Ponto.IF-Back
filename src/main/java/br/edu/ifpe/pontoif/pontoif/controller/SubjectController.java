package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.SubjectDTO;
import br.edu.ifpe.pontoif.pontoif.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
@Tag(name = "Subjects", description = "API for subjects management")
public class SubjectController {

    private final SubjectService service;

    @Operation(summary = "Create a subject",
            responses = @ApiResponse(responseCode = "201"))
    @PostMapping
    public ResponseEntity<SubjectDTO> create(@Valid @RequestBody SubjectDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "List all subjects")
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get subject by ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            })
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> get(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update subject")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> update(@PathVariable UUID id,
                                             @Valid @RequestBody SubjectDTO dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete subject")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}