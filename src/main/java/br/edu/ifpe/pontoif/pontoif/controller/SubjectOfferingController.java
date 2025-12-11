package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.EnrollmentDTO;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectOfferingDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import br.edu.ifpe.pontoif.pontoif.service.SubjectOfferingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offerings")
@RequiredArgsConstructor
@Tag(name = "Subject Offering Management", description = "API to manage subject offerings")
public class SubjectOfferingController {

    private final SubjectOfferingService service;

    @Operation(
            summary = "Create a subject offering",
            description = "Creates a new subject offering",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody SubjectOfferingDTO dto) {
        service.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "List all offerings",
            description = "Retrieves all subject offerings"
    )
    @GetMapping
    public ResponseEntity<List<SubjectOfferingDTO>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "Get offering by ID",
            description = "Retrieve a subject offering by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Offering found"),
                    @ApiResponse(responseCode = "404", description = "Offering not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SubjectOfferingDTO> get(@PathVariable Long id) {
        Optional<SubjectOfferingDTO> dto = service.getById(id);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update offering",
            description = "Updates an existing subject offering"
    )
    @PutMapping("/{id}")
    public ResponseEntity<SubjectOfferingDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SubjectOfferingDTO dto) {

        Optional<SubjectOfferingDTO> updated = service.update(id, dto);

        return updated
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete offering",
            description = "Deletes a subject offering by its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "List enrollments for an offering",
            description = "Returns all students enrolled in a given offering"
    )
    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByOffering(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEnrollmentsByOffering(id));
    }

    @Operation(
            summary = "List attendance for an offering",
            description = "Returns all attendance records for all sessions of an offering"
    )
    @GetMapping("/{id}/attendance")
    public ResponseEntity<List<AttendanceRecord>> attendance(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAttendance(id));
    }
}