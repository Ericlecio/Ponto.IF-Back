package br.edu.ifpe.pontoif.pontoif.service.rabbit;

import br.edu.ifpe.pontoif.pontoif.configurations.RabbitConfig;
import br.edu.ifpe.pontoif.pontoif.mapper.CourseMapper;
import br.edu.ifpe.pontoif.pontoif.dto.CourseDTO;
import br.edu.ifpe.pontoif.pontoif.repository.CourseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class CourseUpdate {

    private final CourseRepository courseRepository;

    public CourseUpdate(final CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_COURSE_CREATED)
    public void receiveMessage(CourseDTO message) {
        var course = courseRepository.findByCorrelationId(message.getId());
        if (course == null)
            return;
        course.setName(message.getName());
        course.setAcronym(message.getAcronym());
        course.setEndTime(message.getEndTime());
        course.setStartTime(message.getStartTime());
        course.setDurationInMonths(message.getDurationInMonths());
        courseRepository.save(course);
    }
}
