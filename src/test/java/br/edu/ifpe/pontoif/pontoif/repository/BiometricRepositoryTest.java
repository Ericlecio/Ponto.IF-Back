package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BiometricRepositoryTest {

    @Autowired
    private BiometricRepository biometricRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindBiometricById() {
        // Given
        User user = new User();
        user.setName("Lucas Fernandes");
        user.setEmail("lucas.fernandes@example.com");
        user.setRegistration("333333");
        user.setIsActive(true);
        user.setType("STUDENT");
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);

        Biometric biometric = new Biometric();
        biometric.setId(123456789L); // ID do sensor
        biometric.setUser(savedUser);

        // When
        Biometric savedBiometric = biometricRepository.saveAndFlush(biometric);

        // Then
        assertThat(savedBiometric.getId()).isEqualTo(123456789L);
        assertThat(savedBiometric.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(savedBiometric.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldFindBiometricById() {
        // Given
        User user = new User();
        user.setName("Mariana Lima");
        user.setEmail("mariana.lima@example.com");
        user.setRegistration("444444");
        user.setIsActive(true);
        user.setType("PROFESSOR");
        user.setRole(Role.PROFESSOR);
        User savedUser = userRepository.save(user);

        Long biometricId = 987654321L;
        Biometric biometric = new Biometric();
        biometric.setId(biometricId);
        biometric.setUser(savedUser);
        biometricRepository.saveAndFlush(biometric);

        // When
        Biometric foundBiometric = biometricRepository.findById(biometricId).orElse(null);

        // Then
        assertThat(foundBiometric).isNotNull();
        assertThat(foundBiometric.getId()).isEqualTo(biometricId);
        assertThat(foundBiometric.getUser().getName()).isEqualTo("Mariana Lima");
    }
}
