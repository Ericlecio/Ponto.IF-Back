package br.edu.ifpe.pontoif.pontoif.controller;


import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.dto.ListPresentDTO;
import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.dto.UserReportDTO;
import br.edu.ifpe.pontoif.pontoif.entity.User;
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

    @GetMapping("/discipline/teacher")
    public ResponseEntity<List<DisciplineDTO>> getDisciplineByUser(User user) {
        return ResponseEntity.ok(userService.getDisciplineByTeacher(user.getId()));
    }

    @GetMapping("/report")
    public ResponseEntity<UserReportDTO> getReportByUser(User user) {
        UserReportDTO report = userService.getReportByUser(user);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/{disciplineId}")
    public ResponseEntity<ListPresentDTO> getReportByUser(User user, @PathVariable("disciplineId") UUID disciplineId) {
        var report = userService.getReportByDiscipline(disciplineId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<UserDTO>> getTeacher() {
        return ResponseEntity.ok(userService.getTeachers());
    }
}
