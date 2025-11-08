package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.dto.UserUpdateDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RegisterUpdate {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE_REGISTER_UPDATE)
    public void receiveMessage(UserUpdateDTO message) {
        var user = userRepository.findById(message.getId());
        if (user.isEmpty())
            return;

        user.ifPresent(
            u -> {
                userMapper.updateEntityFromDTO(message, u);
                userRepository.save(u);
            }
        );
    }
}
