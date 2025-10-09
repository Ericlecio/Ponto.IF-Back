package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.ClassroomMapper;
import br.edu.ifpe.pontoif.pontoif.repository.ClassroomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public boolean deleteClassroom(final UUID uuid) {
        return classroomRepository.findById(uuid).map(classroom -> {
            classroomRepository.delete(classroom);
            return true;
        }).orElse(false);
    }
}
