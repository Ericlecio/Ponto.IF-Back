package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.AttendanceDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.AttendanceMapper;
import br.edu.ifpe.pontoif.pontoif.repository.AttendanceRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository repository;
    private final AttendanceMapper mapper;

    @Transactional
    public void registerAttendance (AttendanceDTO dto) {
        repository.save(mapper.toEntity(dto));
    }

    public List<AttendanceDTO> getAttendanceByOffering(Long offeringId) {
        return repository.findAllByOfferingId(offeringId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendanceBySession(Long sessionId){
        return repository.findAllBySession_Id(sessionId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}