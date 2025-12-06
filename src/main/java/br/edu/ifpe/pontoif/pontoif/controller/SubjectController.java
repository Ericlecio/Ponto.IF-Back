package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.CourseSubjectRequestDTO;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectDTO;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectResponseDTO;
import br.edu.ifpe.pontoif.pontoif.service.SubjectService;
import br.edu.ifpe.pontoif.pontoif.service.SubjectOfferingService;
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

    private final SubjectService subjectService;
    private final SubjectOfferingService subjectOfferingService;

    @Operation(summary = "Create a subject",
            responses = @ApiResponse(responseCode = "201"))
    @PostMapping
    public ResponseEntity<SubjectResponseDTO> create(@Valid @RequestBody SubjectDTO dto) {
        return new ResponseEntity<>(subjectService.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "List all subjects")
    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>> list() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    @Operation(summary = "Get subject by ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            })
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> get(@PathVariable UUID id) {
        return subjectService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update subject")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(@PathVariable UUID id,
                                             @Valid @RequestBody SubjectDTO dto) {
        return subjectService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete subject")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return subjectService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add course in subject")
    @PostMapping("/add-course")
    public ResponseEntity<SubjectResponseDTO> addCourseInSubject(@RequestBody CourseSubjectRequestDTO dto) {
        return subjectService.addCourseInSubject(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove course in subject")
    @PostMapping("/add-course")
    public ResponseEntity<SubjectResponseDTO> removeCourseInSubject(@RequestBody CourseSubjectRequestDTO dto) {
        return subjectService.removeCourseInSubject(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get subjects from course ID")
    @GetMapping("/course/{courseId}/")
    public ResponseEntity<List<SubjectResponseDTO>> getByCourse(@PathVariable UUID courseId) {
        return ResponseEntity.ok(subjectService.getByCourse(courseId));
    }

    @Operation(summary = "Start class session for biometric validation.",
            description = "Allows the teacher in charge to start the lesson. Creates an active session.")
    @PostMapping("/offerings/{offeringId}/start")
    public ResponseEntity<Void> startClass(@PathVariable Long offeringId,
                                           @RequestParam UUID teacherId) {
        subjectOfferingService.startClassSession(offeringId, teacherId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "End class session",
            description = "Allows the teacher in charge to end the active class session.")
    @PostMapping("/offerings/{offeringId}/finalize")
    public ResponseEntity<Void> finalizeClass(@PathVariable Long offeringId,
                                              @RequestParam UUID teacherId) {
        subjectOfferingService.endClassSession(offeringId, teacherId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}