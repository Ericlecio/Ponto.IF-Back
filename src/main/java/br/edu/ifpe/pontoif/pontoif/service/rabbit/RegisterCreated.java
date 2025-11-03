package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class RegisterCreated {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Logger logger;

    public RegisterCreated(final UserRepository userRepository,
                           final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        logger = Logger.getLogger(RegisterCreated.class.getName());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_REGISTER_CREATED)
    public void receiveMessage(UserDTO message) {
        logger.log(Level.INFO, "Received Register Created message for user ID: " + message.toString());
        try {
            if (userRepository.existsByCorrelationId(message.getId()))
                return;
            var user = userMapper.toEntity(message);

            user.setIsActive(true);
            user.setType(Role.STUDENT.name());
            user.setRole(Role.STUDENT);
            if (message.getRole() != null) {
                user.setRole(message.getRole());
                user.setType(message.getRole().name());
            }
            logger.log(Level.INFO, "Register Created user: " + user.toString());
            userRepository.save(user);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error: " + e.getMessage());
        }
    }
}
