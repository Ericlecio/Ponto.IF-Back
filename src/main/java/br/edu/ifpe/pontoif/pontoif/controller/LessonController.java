package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.service.LessonService;
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
@RequestMapping("/lesson")
@RequiredArgsConstructor
@Tag(name = "Lesson management", description = "API to manage lessons")
public class LessonController {

    private final LessonService lessonService;

    @Operation(
            summary = "Register a new lesson",
            description = "Endpoint responsible for adding a new lesson",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created successfully",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping
    public ResponseEntity<Void> createLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        lessonService.insertLesson(lessonDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get lesson by ID",
            description = "Retrieve a lesson by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lesson found"),
                    @ApiResponse(responseCode = "404", description = "Lesson not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable UUID id) {
        return lessonService.getLessonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "List lessons by multiple IDs",
            description = "Retrieve all lessons that match the given list of IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lessons retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid list of IDs provided")
            }
    )
    @GetMapping("/by-ids")
    public ResponseEntity<List<LessonDTO>> getLessonsByIds(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(lessonService.getLessonsByIds(ids));
    }


    @Operation(
            summary = "List all lessons",
            description = "Retrieve all lessons from the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @Operation(
            summary = "Updates a Lesson by its id",
            description = "Endpoint responsible for updating by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Lesson updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Lesson not found"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable UUID id, @Valid @RequestBody LessonDTO lessonDTO) {
        Optional<LessonDTO> updatedLesson = lessonService.updateLesson(id, lessonDTO);
        return updatedLesson.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a lesson",
            description = "Deletes a lesson by its UUID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Lesson not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable UUID id) {
        return lessonService.deleteLesson(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}