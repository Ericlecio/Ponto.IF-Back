package br.edu.ifpe.pontoif.pontoif.controller;

import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "course management", description = "API to manage course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping()
    public ResponseEntity<List<CourseDTO>> getAllCourse(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable UUID id){
        return courseService.getCourseById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<CourseDTO>> getCourseByIds(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(courseService.getCoursesByIds(ids));
    }
}
