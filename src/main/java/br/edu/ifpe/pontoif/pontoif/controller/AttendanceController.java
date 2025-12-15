package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.dto.StudentAttendanceDetailsDTO;
import br.edu.ifpe.pontoif.pontoif.dto.StudentAttendanceReportDTO;
import br.edu.ifpe.pontoif.pontoif.service.AttendanceService;
import br.edu.ifpe.pontoif.pontoif.service.ReportExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "API for attendance management")
public class AttendanceController {

    private final AttendanceService service;
    private final ReportExportService reportExportService;

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

    @Operation(summary = "Get attdance records by session ID")
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<AttendanceDTO>> getBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(service.getAttendanceBySession(sessionId));
    }

    @Operation(summary = "Export attendance report to Excel by offering ID")
    @GetMapping("/offering/{offeringId}/export/excel")
    public ResponseEntity<byte[]> exportAttendanceToExcel(@PathVariable Long offeringId) {
        try {
            List<StudentAttendanceReportDTO> reportData = service.generateAttendanceReport(offeringId);
            byte[] excelData = reportExportService.exportToExcel(reportData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "relatorio_presenca_" + offeringId + ".xlsx");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Export attendance report to CSV by offering ID")
    @GetMapping("/offering/{offeringId}/export/csv")
    public ResponseEntity<String> exportAttendanceToCsv(@PathVariable Long offeringId) {
        try {
            List<StudentAttendanceReportDTO> reportData = service.generateAttendanceReport(offeringId);
            String csvData = reportExportService.exportToCsv(reportData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv; charset=UTF-8"));
            headers.setContentDispositionFormData("attachment", "relatorio_presenca_" + offeringId + ".csv");

            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get attendance details by offering")
    @GetMapping("/report/offering/{offeringId}")
    public ResponseEntity<List<StudentAttendanceDetailsDTO>> getDetailsByOffering(
            @PathVariable Long offeringId
    ) {
        return ResponseEntity.ok(service.getDetailsByOffering(offeringId));
    }
}