package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
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

    @Transactional
    public void insertBiometric(final BiometricsDTO biometricsDTO) {
        biometricRepository.save(biometricMapper.toEntity(biometricsDTO));
    }


    public Optional<BiometricsDTO> getBiometricById(final Long id) {
        return biometricRepository.findById(id).map(biometricMapper::toDTO);
    }

    public List<BiometricsDTO> getAllBiometrics (Long id) {
        return biometricRepository.findAll()
                .stream()
                .map(biometricMapper::toDTO)
                .toList();
    }

    @Transactional
    public boolean deleteBiometric(final Long id) {
        return biometricRepository.findById(id).map( biometric -> {
            biometricRepository.delete(biometric);
            return true;
        }).orElse(false);
    }

    public boolean matchSample(final BiometricSampleDTO biometricSampleDTO) {
        //TODO: Verificar metodo de match, atualmente apenas ID sem template ou match externo
        if (biometricRepository.existsById(biometricSampleDTO.getId()))

            return true;
        return false;
    }
}
