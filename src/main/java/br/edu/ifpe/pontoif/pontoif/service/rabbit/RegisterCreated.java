package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.dto.UserDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Role;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RegisterCreated {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RegisterCreated(final UserRepository userRepository,
                           final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_REGISTER_CREATED)
    public void receiveMessage(UserDTO message) {
        if (userRepository.existsByCorrelationId(message.getId()))
            return;
        var user = userMapper.toEntity(message);
        user.setIsActive(true);
        if (message.getRole() == null) {
            user.setRole(Role.STUDENT);
        }
        userRepository.save(user);
    }
}
