package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.ClassRoomRequestDTO;
import br.edu.ifpe.pontoif.pontoif.dto.ClassRoomResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
import br.edu.ifpe.pontoif.pontoif.exception.NotFoundException;
import br.edu.ifpe.pontoif.pontoif.mapper.ClassRoomMapper;
import br.edu.ifpe.pontoif.pontoif.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    private final ClassroomRepository classroomRepository;
    private final ClassRoomMapper classRoomMapper;

    public ClassRoomResponseDTO create(ClassRoomRequestDTO dto) {
        Classroom entity = classRoomMapper.toEntity(dto);
        Classroom savedEntity = classroomRepository.save(entity);
        return classRoomMapper.toDTO(savedEntity);
    }

    public ClassRoomResponseDTO update(UUID id, ClassRoomRequestDTO dto) {
        Classroom existingEntity = classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classroom not found with id: " + id));

        classRoomMapper.updateEntityFromDto(dto, existingEntity);
        Classroom updatedEntity = classroomRepository.save(existingEntity);
        return classRoomMapper.toDTO(updatedEntity);
    }

    public void delete(UUID id) {
        if (!classroomRepository.existsById(id)) {
            throw new NotFoundException("Classroom not found with id: " + id);
        }
        classroomRepository.deleteById(id);
    }

    public ClassRoomResponseDTO findById(UUID id) {
        return classroomRepository.findById(id)
                .map(classRoomMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Classroom not found with id: " + id));
    }

    public List<ClassRoomResponseDTO> findAll() {
        List<Classroom> entities = classroomRepository.findAll();
        return classRoomMapper.toDTOs(entities);
    }
}
