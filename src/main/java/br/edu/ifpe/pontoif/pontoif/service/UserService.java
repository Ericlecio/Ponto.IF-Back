package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.*;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getTeachers() {
        return userRepository
                .findByRole(Role.TEACHER)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public User getTeacherById(UUID id) {
        return userRepository
                .findByIdAndRole(id, Role.TEACHER)
                .orElse(null);
    }
}
