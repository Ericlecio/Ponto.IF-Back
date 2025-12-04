package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.SubjectOfferingDTO;
import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User management", description = "API to manage data of users")
public class UserController {

    private final UserService userService;

    @GetMapping("/teacher")
    public ResponseEntity<List<UserDTO>> getTeacher() {
        return ResponseEntity.ok(userService.getTeachers());
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<UserDTO> getTeacherById(@PathVariable UUID id) {
        return userService.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teachers/{id}/offerings")
    public ResponseEntity<List<SubjectOfferingDTO>> getTeacherOfferings(UUID id) {
        return ResponseEntity.ok(userService.getTeacherOfferings(id));
    }
}
