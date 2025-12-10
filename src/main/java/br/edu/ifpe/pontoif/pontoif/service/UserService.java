package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.*;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.mapper.SubjectOfferingMapper;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.repository.SubjectOfferingRepository;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubjectOfferingRepository offeringRepository;

    private final SubjectOfferingMapper offeringMapper;
    private final UserMapper userMapper;

    public List<UserDTO> getUser(Role role) {
        return userRepository
                .findByRole(role)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public Optional<UserDTO> getTeacherById(UUID id) {
        return userRepository
                .findByIdAndRole(id, Role.PROFESSOR)
                .map(userMapper::toDTO);
    }

    public List<SubjectOfferingDTO> getTeacherOfferings(UUID teacherId) {
        return offeringRepository.findAllByTeacher_Id(teacherId)
                .stream()
                .map(offeringMapper::toDTO)
                .toList();
    }
}