package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricDTO;
import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
import br.edu.ifpe.pontoif.pontoif.mapper.BiometricMapper;
import br.edu.ifpe.pontoif.pontoif.repository.BiometricRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BiometricService {
    //TODO: Verificar se precisa de update

    private final BiometricRepository biometricRepository;
    private final BiometricMapper biometricMapper;

    private final RecordService recordService;

    private final LessonService lessonService;

    @Transactional
    public void insertBiometric(final BiometricDTO biometricDTO) {
        biometricRepository.save(biometricMapper.toEntity(biometricDTO));
    }

    public Optional<BiometricDTO> getBiometricById(final Long id) {
        return biometricRepository.findById(id).map(biometricMapper::toDTO);
    }

    public List<BiometricDTO> getAllBiometrics(Long id) {
        return biometricRepository.findAll()
                .stream()
                .map(biometricMapper::toDTO)
                .toList();
    }

    @Transactional
    public boolean deleteBiometric(final Long id) {
        return biometricRepository.findById(id).map(biometric -> {
            biometricRepository.delete(biometric);
            return true;
        }).orElse(false);
    }

    @Transactional
    public Optional<Biometric> matchSample(final BiometricSampleDTO biometricSampleDTO) {
        //TODO: Verificar qual método de mach que sera utilizado pelo sistema, atualmente apenas ID
        return biometricRepository.findById(biometricSampleDTO.getId())
                .flatMap(this::processMatchedBiometric);
    }

    private Optional<Biometric> processMatchedBiometric(final Biometric biometric) {
        lessonService.getCurrentLesson(biometric.getUser())
                .ifPresent(lesson ->
                        recordService.insertRecord(createRecordDTO(biometric, lesson)
                ));
        return Optional.of(biometric);
    }

    private RecordDTO createRecordDTO(final Biometric biometric, final Lesson lesson) {
        RecordDTO dto = new RecordDTO();
        dto.setUser(biometric.getUser().getId());
        dto.setLesson(lesson.getId());
        return dto;
    }
}
