package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.DisciplineMapper;
import br.edu.ifpe.pontoif.pontoif.repository.DisciplineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final DisciplineMapper disciplineMapper;

    @Transactional
    public void insertDiscipline(final DisciplineDTO disciplineDTO) {
        disciplineRepository.save(disciplineMapper.toEntity(disciplineDTO));
    }

    public List<DisciplineDTO> getAllDisciplines() {
        return disciplineRepository.findAll()
                .stream()
                .map(disciplineMapper::toDTO)
                .toList();
    }

    public Optional<DisciplineDTO> getDisciplineById(final UUID id) {
        return disciplineRepository.findById(id)
                .map(disciplineMapper::toDTO);
    }

    @Transactional
    public Optional<DisciplineDTO> updateDiscipline (UUID id, final DisciplineDTO disciplineDTO) {
        return disciplineRepository.findById(id).map(existing -> {
            existing.setCorrelationId(disciplineDTO.getCorrelationId());
            existing.setName(disciplineDTO.getName());
            existing.setWorkload(disciplineDTO.getWorkload());
            return disciplineMapper.toDTO(disciplineRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteDiscipline(final UUID uuid) {
        return disciplineRepository.findById(uuid).map(discipline -> {
            disciplineRepository.delete(discipline);
            return true;
        }).orElse(false);
    }
}
