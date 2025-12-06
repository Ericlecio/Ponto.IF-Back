package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.CourseSubjectRequestDTO;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectDTO;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Course;
import br.edu.ifpe.pontoif.pontoif.entity.Subject;
import br.edu.ifpe.pontoif.pontoif.mapper.SubjectMapper;
import br.edu.ifpe.pontoif.pontoif.repository.CourseRepository;
import br.edu.ifpe.pontoif.pontoif.repository.SubjectOfferingRepository;
import br.edu.ifpe.pontoif.pontoif.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository repository;
    private final CourseRepository courseRepository;
    private final SubjectMapper mapper;

    @Transactional
    public SubjectResponseDTO create(SubjectDTO dto) {
        Subject entity = mapper.toEntity(dto);
        List<Course> courses = getCourses(dto);
        entity.setCourses(courses);
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public List<SubjectResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<SubjectResponseDTO> getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public List<SubjectResponseDTO> getByCourse(UUID courseId){
        return repository.findByCourses_Id(courseId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }


    @Transactional
    public Optional<SubjectResponseDTO> update(UUID id, SubjectDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setCode(dto.getCode());
            existing.setDescription(dto.getDescription());
            List<Course> courses = getCourses(dto);
            courses.addAll(existing.getCourses());
            Set<Course> unique = new HashSet<>(courses);
            existing.setCourses(new ArrayList<>(unique));
            var result = repository.save(existing);
            return mapper.toDTO(result);
        });
    }

    @Transactional
    public Optional<SubjectResponseDTO> addCourseInSubject(CourseSubjectRequestDTO dto) {
        return repository.findById(dto.getSubjectId()).map(existing -> {
            courseRepository.findById(dto.getCourseId()).ifPresent(course -> {
                if (!existing.getCourses().contains(course)) {
                    existing.getCourses().add(course);
                }
            });
            var result = repository.save(existing);
            return mapper.toDTO(result);
        });
    }

    @Transactional
    public Optional<SubjectResponseDTO> removeCourseInSubject(CourseSubjectRequestDTO dto) {
        return repository.findById(dto.getSubjectId()).map(existing -> {
            existing.getCourses().removeIf(course -> course.getId().equals(dto.getCourseId()));
            var result = repository.save(existing);
            return mapper.toDTO(result);
        });
    }

    private List<Course> getCourses(SubjectDTO dto) {
        List<Course> courses = new ArrayList<>();
        dto.getCourses().forEach(course -> {
            courseRepository.findById(course).ifPresent(courses::add);
        });
        return courses;
    }

    @Transactional
    public boolean delete(UUID id) {
        return repository.findById(id)
                .map(s -> {
                    repository.delete(s);
                    return true;
                })
                .orElse(false);
    }
}
