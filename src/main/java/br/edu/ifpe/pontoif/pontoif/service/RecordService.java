package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.RecordMapper;
import br.edu.ifpe.pontoif.pontoif.repository.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;

    @Transactional
    public void insertRecord(final RecordDTO recordDTO) {
        recordRepository.save(recordMapper.toEntity(recordDTO));
    }

    public Optional<RecordDTO> getRecordById(final UUID uuid) {
        return recordRepository.findById(uuid).map(recordMapper::toDTO);
    }

    public List<RecordDTO> getAllRecords () {
        return recordRepository.findAll()
                .stream()
                .map(recordMapper::toDTO)
                .toList();
    }

    @Transactional
    public Optional<RecordDTO> updateRecord(UUID uuid, final RecordDTO recordDTO) {
        return recordRepository.findById(uuid).map(existing -> {
           existing.setLesson(existing.getLesson());
           existing.setDate(existing.getDate());
           existing.setUser(existing.getUser());
           existing.setBiometric(existing.getBiometric());
           return recordMapper.toDTO(recordRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteRecord(final UUID uuid) {
        return recordRepository.findById(uuid).map( record -> {
            recordRepository.delete(record);
            return true;
        }).orElse(false);
    }
}
