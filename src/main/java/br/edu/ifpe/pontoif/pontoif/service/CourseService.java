package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.CourseMapper;
import br.edu.ifpe.pontoif.pontoif.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public Optional<CourseDTO> getCourseById(final UUID id) {
        return courseRepository.findById(id).map(courseMapper::toDTO);
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    public List<CourseDTO> getCoursesByIds(List<UUID> ids) {
        return courseRepository.findAllById(ids)
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

}
