package br.edu.ifpe.pontoif.pontoif.service.match;

import br.edu.ifpe.pontoif.pontoif.dto.BiometricMatchResultDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.repository.BiometricRepository;
import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .filter(r -> r.score() > 50)
                .max(Comparator.comparingDouble(MatchResult::score))
                .map(best -> {
                    log.info("🔎 Best match for role {} → user={} (score={})",
                            role, best.biometric().getUser().getName(), best.score());
                    return best.biometric();
                });
    }

    public Optional<BiometricMatchResultDTO> findBestMatch(
            byte[] sampleTemplate,
            Role role,
            Long sessionId) {

        List<Biometric> candidates =
                biometricRepository.findBiometricsBySessionAndRole(
                        sessionId, role
                );

        if (candidates.isEmpty()) {
            log.warn("⚠️ No candidates for role={} session={}", role, sessionId);
            return Optional.empty();
        }

        return candidates.stream()
                .map(b -> new MatchResult(
                        b,
                        afisMatchService.calculateScore(b.getTemplate(), sampleTemplate)
                ))
                .filter(r -> r.score() > 50) // threshold realista
                .max(Comparator.comparingDouble(MatchResult::score))
                .map(best -> {
                    Biometric biometric = best.biometric();
                    double score = best.score();

                    log.info("🔍 Match OK → user={} score={} session={}",
                            biometric.getUser().getName(),
                            score,
                            sessionId);

                    BiometricMatchResultDTO dto = new BiometricMatchResultDTO();
                    dto.setBiometricId(biometric.getId());
                    dto.setStudentId(biometric.getUser().getId());
                    dto.setScore(score);
                    return dto;
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