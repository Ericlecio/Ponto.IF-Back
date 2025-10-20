package br.edu.ifpe.pontoif.pontoif.repository;

import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserById() {
        // Given
        User user = new User();
        user.setName("João Silva");
        user.setEmail("joao.silva@example.com");
        user.setRegistration("123456");
        user.setIsActive(true);
        user.setType("STUDENT");
        user.setRole(Role.STUDENT);

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("João Silva");
        assertThat(savedUser.getEmail()).isEqualTo("joao.silva@example.com");
    }

    @Test
    void shouldFindUserByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();
        User user = new User();
        user.setCorrelationId(correlationId);
        user.setName("Maria Santos");
        user.setEmail("maria.santos@example.com");
        user.setRegistration("654321");
        user.setIsActive(true);
        user.setType("PROFESSOR");
        user.setRole(Role.PROFESSOR);
        userRepository.save(user);

        // When
        User foundUser = userRepository.findByCorrelationId(correlationId);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getCorrelationId()).isEqualTo(correlationId);
        assertThat(foundUser.getName()).isEqualTo("Maria Santos");
    }

    @Test
    void shouldReturnTrueIfUserExistsByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();
        User user = new User();
        user.setCorrelationId(correlationId);
        user.setName("Pedro Oliveira");
        user.setEmail("pedro.oliveira@example.com");
        user.setRegistration("789012");
        user.setIsActive(true);
        user.setType("STUDENT");
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        // When
        Boolean exists = userRepository.existsByCorrelationId(correlationId);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseIfUserDoesNotExistByCorrelationId() {
        // Given
        UUID correlationId = UUID.randomUUID();

        // When
        Boolean exists = userRepository.existsByCorrelationId(correlationId);

        // Then
        assertThat(exists).isFalse();
    }
}
