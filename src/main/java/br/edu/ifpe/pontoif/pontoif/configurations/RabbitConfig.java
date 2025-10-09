package br.edu.ifpe.pontoif.pontoif.configurations;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_REGISTER_CREATED = "ifpe-register-created";
    public static final String QUEUE_REGISTER_UPDATE = "ifpe-register-update";
    public static final String QUEUE_COURSE_CREATED = "ifpe-course-created";
    public static final String QUEUE_COURSE_UPDATE = "ifpe-course-update";
    public static final String QUEUE_DISCIPLINE_CREATED = "ifpe-discipline-created";
    public static final String QUEUE_DISCIPLINE_UPDATE = "ifpe-discipline-update";

    @Bean
    public Declarables queues() {
        return new Declarables(
                new Queue(QUEUE_REGISTER_CREATED, true),
                new Queue(QUEUE_REGISTER_UPDATE, true),
                new Queue(QUEUE_COURSE_CREATED, true),
                new Queue(QUEUE_COURSE_UPDATE, true),
                new Queue(QUEUE_DISCIPLINE_CREATED, true),
                new Queue(QUEUE_DISCIPLINE_UPDATE, true)
        );
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
