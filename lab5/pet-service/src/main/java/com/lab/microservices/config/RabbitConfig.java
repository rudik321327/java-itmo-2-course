package com.lab.microservices.pet.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация RabbitMQ для Pet-Service.
 */
@Configuration
public class RabbitConfig {

    public static final String QUEUE_CAT_GET      = "pet.get";
    public static final String QUEUE_CAT_LIST     = "pet.list";
    public static final String QUEUE_CAT_CREATE   = "pet.create";
    public static final String QUEUE_CAT_UPDATE   = "pet.update";
    public static final String QUEUE_CAT_DELETE   = "pet.delete";
    public static final String QUEUE_CAT_BY_OWNER = "pet.byOwner";

    @Bean
    public Queue catGetQueue() {
        return new Queue(QUEUE_CAT_GET);
    }

    @Bean
    public Queue catListQueue() {
        return new Queue(QUEUE_CAT_LIST);
    }

    @Bean
    public Queue catCreateQueue() {
        return new Queue(QUEUE_CAT_CREATE);
    }

    @Bean
    public Queue catUpdateQueue() {
        return new Queue(QUEUE_CAT_UPDATE);
    }

    @Bean
    public Queue catDeleteQueue() {
        return new Queue(QUEUE_CAT_DELETE);
    }

    @Bean
    public Queue catByOwnerQueue() {
        return new Queue(QUEUE_CAT_BY_OWNER);
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
