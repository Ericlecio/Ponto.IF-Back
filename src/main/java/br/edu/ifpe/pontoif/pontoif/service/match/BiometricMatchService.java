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

    /**
     * Match biométrico considerando sessão e role.
     */
    public Optional<BiometricMatchResultDTO> findBestMatch(
            byte[] sampleTemplate,
            Role role,
            Long sessionId
    ) {

        List<Biometric> candidates =
                biometricRepository.findBiometricsBySessionAndRole(
                        sessionId, role
                );

        if (candidates.isEmpty()) {
            log.warn("⚠️ No candidates for role={} session={}", role, sessionId);
            return Optional.empty();
        }

        MatchResult best =
                candidates.stream()
                        .map(b ->
                                afisMatchService
                                        .calculateScoreSafe(
                                                b.getTemplate(),
                                                sampleTemplate
                                        )
                                        .map(score -> new MatchResult(b, score))
                        )
                        .flatMap(Optional::stream)
                        .max(Comparator.comparingDouble(MatchResult::score))
                        .orElseThrow();

        if (best.score() < 50) {
            log.warn(
                    "❌ Biometric rejected → user={} score={} session={}",
                    best.biometric().getUser().getName(),
                    best.score(),
                    sessionId
            );
            throw new RuntimeException("Low score: " + best.score());
        }

        log.info(
                "✅ Biometric accepted → user={} score={} session={}",
                best.biometric().getUser().getName(),
                best.score(),
                sessionId
        );

        Biometric biometric = best.biometric();

        BiometricMatchResultDTO dto = new BiometricMatchResultDTO();
        dto.setBiometricId(biometric.getId());
        dto.setStudentId(biometric.getUser().getId());
        dto.setScore(best.score());

        return Optional.of(dto);
    }

    /**
     * Match genérico por role (sem sessão).
     */
    public Optional<Biometric> findMatchByRole(
            byte[] sampleTemplate,
            Role role
    ) {

        List<Biometric> candidates =
                biometricRepository.findAllByUser_Role(role);

        if (candidates.isEmpty()) {
            log.warn("⚠️ No biometric candidates found for role: {}", role);
            return Optional.empty();
        }

        return candidates.stream()
                .map(b ->
                        afisMatchService
                                .calculateScoreSafe(
                                        b.getTemplate(),
                                        sampleTemplate
                                )
                                .map(score -> new MatchResult(b, score))
                )
                .flatMap(Optional::stream)
                .filter(r -> r.score() > 50)
                .max(Comparator.comparingDouble(MatchResult::score))
                .map(best -> best.biometric());
    }

    public Optional<Biometric> findTeacherMatch(byte[] sampleTemplate) {
        return findMatchByRole(sampleTemplate, Role.PROFESSOR);
    }

    public Optional<Biometric> findStudentMatch(byte[] sampleTemplate) {
        return findMatchByRole(sampleTemplate, Role.STUDENT);
    }

    private record MatchResult(Biometric biometric, double score) {}
}