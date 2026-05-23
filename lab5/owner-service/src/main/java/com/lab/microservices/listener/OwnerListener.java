package com.lab.microservices.owner.listener;

import com.lab.microservices.owner.config.RabbitConfig;
import com.lab.microservices.owner.dto.OwnerCreateRequest;
import com.lab.microservices.owner.dto.OwnerRequest;
import com.lab.microservices.owner.dto.OwnerResponse;
import com.lab.microservices.owner.service.OwnerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ Listener для очередей: owner.get и owner.create.
 * При получении сообщения автоматом десериализуется JSON → DTO, затем вызывается нужный метод сервиса,
 * а возвращаемый DTO автоматически сериализуется в JSON и возвращается по reply-to.
 */
@Component
public class OwnerListener {

    private final OwnerService ownerService;

    public OwnerListener(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Слушает очередь "owner.get".
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_OWNER_GET)
    public OwnerResponse handleGetOwner(OwnerRequest request) {
        return ownerService.getOwner(request);
    }

    /**
     * Слушает очередь "owner.create".
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_OWNER_CREATE)
    public OwnerResponse handleCreateOwner(OwnerCreateRequest request) {
        return ownerService.createOwner(request);
    }
}
