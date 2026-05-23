package com.lab.microservices.owner.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация RabbitMQ для Owner-Service.
 */
@Configuration
public class RabbitConfig {

    // Имена очередей должны совпадать с теми, что объявлены в Web-Gateway.
    public static final String QUEUE_OWNER_GET    = "owner.get";
    public static final String QUEUE_OWNER_CREATE = "owner.create";

    @Bean
    public Queue ownerGetQueue() {
        return new Queue(QUEUE_OWNER_GET);
    }

    @Bean
    public Queue ownerCreateQueue() {
        return new Queue(QUEUE_OWNER_CREATE);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}
