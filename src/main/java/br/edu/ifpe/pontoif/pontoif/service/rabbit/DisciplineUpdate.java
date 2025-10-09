package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.DisciplineMapper;
import br.edu.ifpe.pontoif.pontoif.repository.DisciplineRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DisciplineUpdate {

    private final DisciplineRepository disciplineRepository;

    public DisciplineUpdate(final DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_DISCIPLINE_UPDATE)
    public void receiveMessage(DisciplineDTO message) {
        if (!disciplineRepository.existsByCorrelationId(message.getId()))
            return;
        var discipline = disciplineRepository.findByCorrelationId(message.getId());
        discipline.setName(message.getName());
        discipline.setWorkload(message.getWorkload());
        disciplineRepository.save(discipline);
    }
}
