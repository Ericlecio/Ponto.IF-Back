package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.SubjectDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Subject;
import br.edu.ifpe.pontoif.pontoif.mapper.SubjectMapper;
import br.edu.ifpe.pontoif.pontoif.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository repository;
    private final SubjectMapper mapper;

    @Transactional
    public SubjectDTO create(SubjectDTO dto) {
        Subject entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public List<SubjectDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<SubjectDTO> getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }


    @Transactional
    public Optional<SubjectDTO> update(UUID id, SubjectDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setCode(dto.getCode());
            existing.setDescription(dto.getDescription());

            repository.save(existing);
            return mapper.toDTO(existing);
        });
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
