package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.mapper.CourseMapper;
import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.repository.CourseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class CourseCreated {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseCreated(final CourseRepository courseRepository,
                         final CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_COURSE_CREATED)
    public void receiveMessage(CourseDTO message) {
        if (courseRepository.existsByCorrelationId(message.getId()))
            return;
        var course = courseMapper.toEntity(message);
        courseRepository.save(course);
    }
}
