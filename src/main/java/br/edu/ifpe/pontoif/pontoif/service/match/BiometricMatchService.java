package br.edu.ifpe.pontoif.pontoif.service.match;

import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
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
    private final SourceAfisMatchService afisMatchService;

    public Optional<Biometric> findMatchByRole(byte[] sampleTemplate, Role role) {
        List<Biometric> candidates = biometricRepository.findAllByUser_Role(role);

        if (candidates.isEmpty()) {
            log.warn("⚠️ No biometric candidates found for role: {}", role);
            return Optional.empty();
        }

        return candidates.stream()
                .filter(b -> b.getTemplate() != null && b.getUser() != null)
                .map(b -> new MatchResult(b, afisMatchService.calculateScore(b.getTemplate(), sampleTemplate)))
                .filter(r -> r.score() > 0)
                .max(Comparator.comparingDouble(MatchResult::score))
                .map(best -> {
                    log.info("🔎 Best match for role {} → user={} (score={})",
                            role, best.biometric().getUser().getName(), best.score());
                    return best.biometric();
                });
    }

    public Optional<Biometric> findBestMatch(byte[] sampleTemplate) {
        List<Biometric> candidates = biometricRepository.findAll();

        return candidates.stream()
                .filter(b -> b.getTemplate() != null && b.getUser() != null)
                .map(b -> new MatchResult(b, afisMatchService.calculateScore(b.getTemplate(), sampleTemplate)))
                .filter(r -> r.score() > 0)
                .max(Comparator.comparingDouble(MatchResult::score))
                .map(best -> {
                    log.info("🔍 Best global match → user={} (score={})",
                            best.biometric().getUser().getName(), best.score());
                    return best.biometric();
                });
    }

    public Optional<Biometric> findTeacherMatch(byte[] sampleTemplate) {
        return findMatchByRole(sampleTemplate, Role.PROFESSOR);
    }

    public Optional<Biometric> findStudentMatch(byte[] sampleTemplate) {
        return findMatchByRole(sampleTemplate, Role.STUDENT);
    }

    private record MatchResult(Biometric biometric, double score) {}
}