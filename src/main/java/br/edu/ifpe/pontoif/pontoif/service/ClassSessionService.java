package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.SessionResponseDTO;
import br.edu.ifpe.pontoif.pontoif.exception.NotFoundException;
import br.edu.ifpe.pontoif.pontoif.mapper.ClassSessionMapper;
import br.edu.ifpe.pontoif.pontoif.repository.ClassSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final ClassSessionMapper mapper;

    public SessionResponseDTO getActualSessionId(Long offeringId) {
        log.info("Fetching actual session ID for offering ID: {}", offeringId);
        return classSessionRepository.findAllByOffering_Id(offeringId).stream()
                .filter(session -> session.getSessionStart().isBefore(java.time.Instant.now().minusNanos(-180)) &&
                                   (session.getSessionEnd() == null || session.getSessionEnd().isAfter(java.time.Instant.now())))
                .findFirst()
                .map(session -> {
                    log.info("Found actual session ID: {} for offering ID: {}", session.getId(), offeringId);
                    return mapper.toDTO(session);
                })
                .orElseThrow(
                        () -> {
                            log.warn("No actual session found for offering ID: {}", offeringId);
                            return new NotFoundException("No actual session found for the given offering ID");
                        }
                );
    }
}
