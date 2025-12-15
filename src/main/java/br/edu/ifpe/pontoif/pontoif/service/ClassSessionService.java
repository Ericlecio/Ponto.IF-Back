package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.SessionResponseDTO;
import br.edu.ifpe.pontoif.pontoif.entity.ClassSession;
import br.edu.ifpe.pontoif.pontoif.exception.NotFoundException;
import br.edu.ifpe.pontoif.pontoif.mapper.ClassSessionMapper;
import br.edu.ifpe.pontoif.pontoif.repository.ClassSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final ClassSessionMapper mapper;

    public SessionResponseDTO getActualSessionId(Long offeringId) {
        log.info("Fetching actual session ID for offering ID: {}", offeringId);
        Instant now = Instant.now();
        return classSessionRepository.findAllByOffering_Id(offeringId).stream()
                .filter(session ->
                        session.getSessionStart().isBefore(now) &&
                                (session.getSessionEnd() == null || session.getSessionEnd().isAfter(now))
                )
                .findFirst()
                .map(session -> {
                    log.info("Found actual session ID: {} for offering ID: {}", session.getId(), offeringId);
                    return mapper.toDTO(session);
                })
                .orElseThrow(() -> {
                    log.warn("No actual session found for offering ID: {}", offeringId);
                    return new NotFoundException("No actual session found for the given offering ID");
                });
    }

    public List<SessionResponseDTO> getAllActiveSessions() {
        Instant now = Instant.now();
        List<ClassSession> sessions = classSessionRepository.findAllActiveSessions(now);
        return sessions.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<SessionResponseDTO> getAllBySubject(UUID subjectId) {
        return classSessionRepository.findAllBySubjectId(subjectId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<SessionResponseDTO> getAllByOffering(Long offeringId){
        return classSessionRepository.findAllByOffering_Id(offeringId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
