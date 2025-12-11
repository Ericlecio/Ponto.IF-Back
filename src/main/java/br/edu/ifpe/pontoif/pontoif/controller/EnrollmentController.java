package br.edu.ifpe.pontoif.pontoif.controller;



import br.edu.ifpe.pontoif.pontoif.dto.EnrollmentDTO;
import br.edu.ifpe.pontoif.pontoif.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollement")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentDTO> registerEnrollment(@Valid @RequestBody EnrollmentDTO dto) {
        enrollmentService.registerEnrollment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getById(@PathVariable UUID id) {
        return enrollmentService.getEnrollmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollment());
    }

    @GetMapping("student/{id}")
    public ResponseEntity<List<EnrollmentDTO>> getStudentEnrollments(@PathVariable UUID id) {
        return ResponseEntity.ok(enrollmentService.getByStudentId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> update(
            @PathVariable UUID id,
            @RequestBody EnrollmentDTO dto) {

        return enrollmentService.updateEnrollment(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = enrollmentService.delete(id);

        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
