package com.lab.microservices.gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация RabbitMQ для Web-Gateway.
 * Объявляем все очереди, которые используются в микросервисах owner-service и pet-service.
 */
@Configuration
public class RabbitConfig {

    // Очереди для Owner-Service
    public static final String QUEUE_OWNER_GET    = "owner.get";
    public static final String QUEUE_OWNER_CREATE = "owner.create";

    // Очереди для Pet-Service
    public static final String QUEUE_PET_GET      = "pet.get";
    public static final String QUEUE_PET_LIST     = "pet.list";
    public static final String QUEUE_PET_CREATE   = "pet.create";
    public static final String QUEUE_PET_UPDATE   = "pet.update";
    public static final String QUEUE_PET_DELETE   = "pet.delete";
    public static final String QUEUE_PET_BY_OWNER = "pet.byOwner";

    @Bean
    public Queue ownerGetQueue() {
        return new Queue(QUEUE_OWNER_GET);
    }

    @Bean
    public Queue ownerCreateQueue() {
        return new Queue(QUEUE_OWNER_CREATE);
    }

    @Bean
    public Queue petGetQueue() {
        return new Queue(QUEUE_PET_GET);
    }

    @Bean
    public Queue petListQueue() {
        return new Queue(QUEUE_PET_LIST);
    }

    @Bean
    public Queue petCreateQueue() {
        return new Queue(QUEUE_PET_CREATE);
    }

    @Bean
    public Queue petUpdateQueue() {
        return new Queue(QUEUE_PET_UPDATE);
    }

    @Bean
    public Queue petDeleteQueue() {
        return new Queue(QUEUE_PET_DELETE);
    }

    @Bean
    public Queue petByOwnerQueue() {
        return new Queue(QUEUE_PET_BY_OWNER);
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
