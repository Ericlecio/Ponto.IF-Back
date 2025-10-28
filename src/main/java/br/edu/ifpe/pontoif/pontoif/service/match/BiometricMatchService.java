package br.edu.ifpe.pontoif.pontoif.service.match;

import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.repository.BiometricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BiometricMatchService {

    private final BiometricRepository biometricRepository;
    private final SourceAfisMatchService matchService;

    public Optional<Biometric> findBestMatch(byte[] sampleTemplate) {
        List<Biometric> all = biometricRepository.findAll();

        return all.stream()
                .filter(b -> b.getTemplate() != null && b.getTemplate().length > 0)
                .map(b -> new MatchResult(b, matchService.calculateScore(b.getTemplate(), sampleTemplate)))
                .max(Comparator.comparingDouble(MatchResult::score))
                .filter(r -> r.score() >= 40.0)
                .map(MatchResult::biometric);
    }

    private record MatchResult(Biometric biometric, double score) {}
}