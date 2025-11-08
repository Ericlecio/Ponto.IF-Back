package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.mapper.CourseMapper;
import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CourseUpdate {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE_COURSE_CREATED)
    public void receiveMessage(CourseDTO message) {
        var course = courseRepository.findById(message.getId());
        if (course.isEmpty())
            return;
        course.ifPresent(c -> {
            courseMapper.updateEntityFromDTO(message, c);
            courseRepository.save(c);
        });
    }
}
