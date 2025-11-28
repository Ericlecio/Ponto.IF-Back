package br.edu.ifpe.pontoif.pontoif.controller;


import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.dto.AttendanceRequestDTO;
import br.edu.ifpe.pontoif.pontoif.entity.AttendanceRecord;
import br.edu.ifpe.pontoif.pontoif.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<List<AttendanceDTO>> registerAttendance(
            @RequestBody AttendanceRequestDTO dto
    ) {
        List<AttendanceRecord> saved = attendanceService.registerAttendances(dto);

        List<AttendanceDTO> response = saved.stream().map(r ->
                AttendanceDTO.builder()
                        .id(r.getId())
                        .session(r.getSession())
                        .student(r.getStudent())
                        .recordedAt(r.getRecordedAt())
                        .status(r.getStatus())
                        .confidence(r.getConfidence())
                        .build()
        ).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/offering/{offeringId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByOffering(@PathVariable Long offeringId) {
        List<AttendanceDTO> list = attendanceService.getAttendanceByOffering(offeringId);
        return ResponseEntity.ok(list);
    }

}
