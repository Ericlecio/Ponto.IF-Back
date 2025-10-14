package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import br.edu.ifpe.pontoif.pontoif.mapper.ClassroomMapper;
import br.edu.ifpe.pontoif.pontoif.repository.ClassroomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    //TODO: Implementar update

    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;

    @Transactional
    public void insertClassroom(final ClassroomDTO classroomDTO) {
        classroomRepository.save(classroomMapper.toEntity(classroomDTO));
    }

    public Optional<ClassroomDTO> getClassroomById(final UUID uuid) {
        return classroomRepository.findById(uuid).map(classroomMapper::toDTO);
    }

    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll()
                .stream()
                .map(classroomMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<UUID> getDisciplineIdsByClassroomId(UUID classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found: " + classroomId));

        return classroom.getDisciplines()
                .stream()
                .map(Discipline::getId)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteClassroom(final UUID uuid) {
        return classroomRepository.findById(uuid).map(classroom -> {
            classroomRepository.delete(classroom);
            return true;
        }).orElse(false);
    }

    @Transactional
    public Optional<ClassroomDTO> updateClassroom(final UUID uuid, final ClassroomDTO classroomDTO) {
        return classroomRepository.findById(uuid)
            .map(existingClassroom -> {
                classroomMapper.updateEntityFromDto(classroomDTO, existingClassroom);
                Classroom updatedClassroom = classroomRepository.save(existingClassroom);
                return classroomMapper.toDTO(updatedClassroom);
            }
        );
    }
}
