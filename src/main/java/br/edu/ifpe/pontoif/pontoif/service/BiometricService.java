package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.*;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.mapper.BiometricMapper;
import br.edu.ifpe.pontoif.pontoif.repository.BiometricRepository;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import br.edu.ifpe.pontoif.pontoif.service.match.BiometricMatchService;
import com.machinezoo.sourceafis.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BiometricService {

    private final BiometricRepository biometricRepository;
    private final BiometricMapper biometricMapper;
    private final BiometricMatchService biometricMatchService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Transactional
    public void insertBiometric(final BiometricDTO biometricDTO) {
        try {
            if (biometricDTO.getImage() == null || biometricDTO.getImage().length == 0) {
                throw new IllegalArgumentException("Missing biometric image.");
            }

            User user = userRepository.findById(biometricDTO.getUser())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            FingerprintTemplate tpl = new FingerprintTemplate(
                    new FingerprintImage(biometricDTO.getImage())
            );
            byte[] serialized = tpl.toByteArray();

            Biometric entity = biometricMapper.toEntity(biometricDTO);
            entity.setTemplate(serialized);
            entity.setUser(user);

            biometricRepository.save(entity);
            log.info("Template saved successfully  ({} bytes)", serialized.length);

        } catch (Exception e) {
            log.error("Error converting image to template : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Optional<TokenDTO> matchAuth(final BiometricSampleDTO dto) {
        try {
            if (dto.getImage() == null || dto.getImage().length == 0) {
                throw new IllegalArgumentException("Missing biometric image.");
            }

            FingerprintTemplate sampleTemplate = new FingerprintTemplate(
                    new FingerprintImage(dto.getImage())
            );

            var biometric = biometricMatchService.findTeacherMatch(sampleTemplate.toByteArray());

            if (biometric.isEmpty()) {
                return Optional.empty();
            }
            var token = tokenService.generateToken(biometric.get().getUser());
            return Optional.of(new TokenDTO(token));

        } catch (Exception e) {
            log.error("Error generating SourceAFIS template from image: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<BiometricMatchResultDTO> matchSample(final BiometricSampleDTO dto) {
        try {
            if (dto.getImage() == null || dto.getImage().length == 0) {
                throw new IllegalArgumentException("Missing biometric image.");
            }

            FingerprintTemplate sampleTemplate = new FingerprintTemplate(
                    new FingerprintImage(dto.getImage())
            );

            Optional<BiometricMatchResultDTO> matchOpt =
                    biometricMatchService.findBestMatch(sampleTemplate.toByteArray());

            matchOpt.ifPresent(matchResult -> {
                biometricRepository.findById(matchResult.getBiometricId())
                        .ifPresent(this::processMatchedBiometric);
            });

            return matchOpt;

        } catch (Exception e) {
            log.error("Error generating SourceAFIS template from image: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private Biometric processMatchedBiometric(Biometric biometric) {
//        lessonService.getCurrentLesson(biometric.getUser())
//                .ifPresent(lesson ->
//                        recordService.insertRecord(createRecordDTO(biometric, lesson)));
//        return biometric;
        return null;
    }

//    private RecordDTO createRecordDTO(Biometric biometric, Lesson lesson) {
//        RecordDTO dto = new RecordDTO();
//        dto.setUser(biometric.getUser().getId());
//        dto.setLesson(lesson.getId());
//        return null;
//    }


    public List<BiometricDTO> getAllBiometrics() {
        return biometricRepository.findAll().stream()
                .map(biometricMapper::toDTO)
                .toList();
    }

    public Optional<BiometricDTO> getBiometricById(final UUID id) {
        return biometricRepository.findById(id).map(biometricMapper::toDTO);
    }

    @Transactional
    public boolean deleteBiometric(final UUID id) {
        return biometricRepository.findById(id)
                .map(b -> { biometricRepository.delete(b); return true; })
                .orElse(false);
    }
}