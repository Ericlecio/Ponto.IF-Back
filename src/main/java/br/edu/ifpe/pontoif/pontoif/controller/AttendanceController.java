package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "API for attendance management")
public class AttendanceController {

    private final AttendanceService service;

    @Operation(summary = "Register attendance record")
    @PostMapping
    public ResponseEntity<AttendanceDTO> registerAttendance( @Valid @RequestBody AttendanceDTO dto) {
        service.registerAttendance(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get attendance records by offering ID")
    @GetMapping("/offering/{offeringId}")
    public ResponseEntity<List<AttendanceDTO>> getByOffering(@PathVariable Long offeringId) {
        return ResponseEntity.ok(service.getAttendanceByOffering(offeringId));
    }
}