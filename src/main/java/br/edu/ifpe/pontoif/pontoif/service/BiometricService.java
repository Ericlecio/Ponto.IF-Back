package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.*;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.mapper.BiometricMapper;
import br.edu.ifpe.pontoif.pontoif.repository.BiometricRepository;
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

    @Transactional
    public void insertBiometric(final BiometricDTO biometricDTO) {
        try {
            if (biometricDTO.getImage() == null || biometricDTO.getImage().length == 0) {
                throw new IllegalArgumentException("Missing biometric image.");
            }

            FingerprintTemplate tpl = new FingerprintTemplate(
                    new FingerprintImage(biometricDTO.getImage())
            );
            byte[] serialized = tpl.toByteArray();

            Biometric entity = biometricMapper.toEntity(biometricDTO);
            entity.setTemplate(serialized);

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
    public Optional<Biometric> matchSample(final BiometricSampleDTO dto) {
        try {
            if (dto.getImage() == null || dto.getImage().length == 0) {
                throw new IllegalArgumentException("Missing biometric image.");
            }

            FingerprintTemplate sampleTemplate = new FingerprintTemplate(
                    new FingerprintImage(dto.getImage())
            );

            return Optional.empty();

        } catch (Exception e) {
            log.error("Error generating SourceAFIS template from image: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }



    public List<BiometricDTO> getAllBiometrics() {
        return biometricRepository.findAll().stream()
                .map(biometricMapper::toDTO)
                .toList();
    }

    public Optional<BiometricDTO> getBiometricById(final Long id) {
        return biometricRepository.findById(id).map(biometricMapper::toDTO);
    }

    @Transactional
    public boolean deleteBiometric(final Long id) {
        return biometricRepository.findById(id)
                .map(b -> { biometricRepository.delete(b); return true; })
                .orElse(false);
    }
}