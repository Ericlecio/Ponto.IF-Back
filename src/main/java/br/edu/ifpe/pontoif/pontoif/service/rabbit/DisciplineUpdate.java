package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.dto.SubjectDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.SubjectMapper;
import br.edu.ifpe.pontoif.pontoif.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisciplineUpdate {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE_DISCIPLINE_UPDATE)
    public void receiveMessage(SubjectDTO message) {
        if (!subjectRepository.existsById(message.getId()))
            return;
        var discipline = subjectRepository.findById(message.getId());
        discipline.ifPresent(subject -> {
            subjectMapper.updateEntityFromDTO(message, subject);
            subjectRepository.save(subject);
        });

    }
}
