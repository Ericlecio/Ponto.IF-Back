package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.service.ClassroomService;
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
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Tag(name = "Classroom management", description = "API to manage classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Operation(
            summary = "Register a new classroom",
            description = "Endpoint responsible for adding a new classroom",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping
    public ResponseEntity<Void> createClassroom(@Valid @RequestBody ClassroomDTO classroomDTO) {
        classroomService.insertClassroom(classroomDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get classroom by ID",
            description = "Retrieve a classroom by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Classroom found"),
                    @ApiResponse(responseCode = "404", description = "Classroom not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable UUID id) {
        return classroomService.getClassroomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "List all classrooms",
            description = "Retrieve all classrooms from the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @Operation(
            summary = "Delete a classroom",
            description = "Deletes a classroom by its UUID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Classroom not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable UUID id) {
        boolean deleted = classroomService.deleteClassroom(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "List all course IDs",
            description = "Recover all classroom subjects",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Disciplines not found")
            }
    )
    @GetMapping("/{id}/disciplines")
    public ResponseEntity<List<UUID>> getDisciplineIds(@PathVariable UUID id) {
        List<UUID> disciplineIds = classroomService.getDisciplineIdsByClassroomId(id);
        return ResponseEntity.ok(disciplineIds);
    }


    @Operation(
            summary = "Update a classroom",
            description = "Updates an existing classroom by its UUID. Any null fields in the request will be ignored.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Classroom updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClassroomDTO.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Classroom not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroom(@PathVariable final UUID id, @RequestBody final ClassroomDTO classroomDTO) {
        final Optional<ClassroomDTO> updatedClassroom = classroomService.updateClassroom(id, classroomDTO);
        return updatedClassroom
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}