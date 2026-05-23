package com.lab.microservices.gateway.service;

import com_lab_microservices_gateway_model.Owner;
import com_lab_microservices_gateway_repository.OwnerRepository;
import com_lab_microservices_gateway_config.RabbitConfig;
import com_lab_microservices_pet_dto.CatGetRequest;
import com_lab_microservices_pet_dto.CatResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Сервис для проверки, является ли текущий пользователь владельцем кота.
 */
@Component
public class SecurityService {

    private final RabbitTemplate rabbitTemplate;
    private final OwnerRepository ownerRepository;

    public SecurityService(RabbitTemplate rabbitTemplate, OwnerRepository ownerRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.ownerRepository = ownerRepository;
    }

    /**
     * Возвращает true, если текущий аутентифицированный пользователь
     * совпадает с ownerId кота (который приходит из Pet-Service).
     */
    public boolean isOwnerOfCat(Long catId) {
        CatResponse catResponse = (CatResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_GET, new CatGetRequest(catId));
        if (catResponse == null) {
            return false;
        }
        Long ownerIdFromCat = catResponse.getOwnerId();

        // Получаем username текущего пользователя из SecurityContext
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Owner owner = ownerRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        return owner.getId().equals(ownerIdFromCat);
    }
}
