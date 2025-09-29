package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.DisciplineMapper;
import br.edu.ifpe.pontoif.pontoif.repository.DisciplineRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DisciplineCreated {

    private final DisciplineRepository disciplineRepository;
    private final DisciplineMapper disciplineMapper;

    public DisciplineCreated(final DisciplineRepository disciplineRepository,
                             final DisciplineMapper disciplineMapper) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_DISCIPLINE_CREATED)
    public void receiveMessage(DisciplineDTO message) {
        if (disciplineRepository.existsByCorrelationId(message.getId()))
            return;
        var discipline = disciplineMapper.toEntity(message);
        disciplineRepository.save(discipline);
    }
}
