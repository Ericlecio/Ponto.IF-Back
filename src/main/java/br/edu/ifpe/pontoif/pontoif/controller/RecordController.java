package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import br.edu.ifpe.pontoif.pontoif.service.RecordService;
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
@RequestMapping("/record")
@RequiredArgsConstructor
@Tag(name = "Record management", description = "API to manage attendance records")
public class RecordController {

    private final RecordService recordService;

    @Operation(
            summary = "Register a new record",
            description = "Endpoint responsible for inserting a new record",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Record created successfully",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<Void> createRecord(@Valid @RequestBody RecordDTO recordDTO) {
        recordService.insertRecord(recordDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get record by ID",
            description = "Retrieve a record by its UUID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Record found"),
                    @ApiResponse(responseCode = "404", description = "Record not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RecordDTO> getRecordById(@PathVariable UUID id) {
        return recordService.getRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "List records by multiple IDs",
            description = "Retrieve all attendance records that match the given list of IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Records retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Invalid list of IDs provided")
            }
    )
    @GetMapping("/by-ids")
    public ResponseEntity<List<RecordDTO>> getRecordsByIds(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(recordService.getRecordsByIds(ids));
    }

    @Operation(
            summary = "List all records",
            description = "Retrieve all attendance records from the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of records retrieved successfully")
            }
    )
    @GetMapping("")
    public ResponseEntity<List<RecordDTO>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    @Operation(
            summary = "Updates a Record by its id",
            description = "Endpoint responsible for updating by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Record updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Record not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RecordDTO> updateRecord(@PathVariable UUID id, @Valid @RequestBody RecordDTO recordDTO) {
        Optional<RecordDTO> updatedRecord = recordService.updateRecord(id, recordDTO);
        return updatedRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Delete a record",
            description = "Deletes a record by its UUID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Record deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Record not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable UUID id) {
        return recordService.deleteRecord(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}