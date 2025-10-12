package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.service.DisciplineService;
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
@RequestMapping("/discipline")
@RequiredArgsConstructor
@Tag(name = "Discipline management", description = "API to manage disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    @Operation(
            summary = "Resister a new discipline",
            description = "Endpoint responsible for adding a new discipline",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created successfully",
                            content = @Content (schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<Void> createDiscipline (@Valid @RequestBody DisciplineDTO disciplineDTO) {
        disciplineService.insertDiscipline(disciplineDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get discipline by ID",
            description = "Retrieve a discipline by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Discipline found"),
                    @ApiResponse(responseCode = "404", description = "Discipline not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDisciplineById(@PathVariable UUID id){
        return disciplineService.getDisciplineById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "List disciplines by multiple IDs",
            description = "Retrieve all disciplines that match the given list of IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplines retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid list of IDs provided")
            }
    )
    @GetMapping("/by-ids")
    public ResponseEntity<List<DisciplineDTO>> getDisciplinesByIds(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(disciplineService.getDisciplinesByIds(ids));
    }

    @Operation(
            summary = "List all disciplines",
            description = "Retrieve all disciplines from the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
            }
    )
    @GetMapping("")
    public ResponseEntity<List<DisciplineDTO>> getAllDisciplines(){
        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @Operation(
            summary = "Update a discipline by its id",
            description = "Endpoint responsible for updating by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Discipline updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Discipline not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<DisciplineDTO> updateDiscipline(@PathVariable UUID id, @Valid @RequestBody DisciplineDTO disciplineDTO){
        Optional<DisciplineDTO> updatedDiscipline = disciplineService.updateDiscipline(id, disciplineDTO);
        return updatedDiscipline.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a discipline",
            description = "Deletes a discipline by its UUID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Discipline deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Discipline not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable UUID id){
        return disciplineService.deleteDiscipline(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
