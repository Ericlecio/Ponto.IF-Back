package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.ClassRoomRequestDTO;
import br.edu.ifpe.pontoif.pontoif.dto.ClassRoomResponseDTO;
import br.edu.ifpe.pontoif.pontoif.service.ClassRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    @PostMapping
    public ResponseEntity<ClassRoomResponseDTO> create(@Valid @RequestBody ClassRoomRequestDTO dto) {
        ClassRoomResponseDTO createdDto = classRoomService.create(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoomResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ClassRoomRequestDTO dto) {
        ClassRoomResponseDTO updatedDto = classRoomService.update(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        classRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomResponseDTO> findById(@PathVariable UUID id) {
        ClassRoomResponseDTO dto = classRoomService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ClassRoomResponseDTO>> findAll() {
        List<ClassRoomResponseDTO> dtoList = classRoomService.findAll();
        return ResponseEntity.ok(dtoList);
    }
}
