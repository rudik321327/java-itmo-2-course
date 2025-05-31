package com.lab.microservices.gateway.service;

import com.lab.microservices.gateway.dto.OwnerDTO;
import com_lab_microservices_gateway_mapper.CatMapper;
import com_lab_microservices_gateway_config.RabbitConfig;
import com.lab.microservices.gateway.dto.OwnerDTO;
import com_lab_microservices_gateway_dto.CatDTO;
import com_lab_microservices_gateway_dto.OwnerCreateRequest;
import com_lab_microservices_gateway_dto.OwnerRequest;
import com_lab_microservices_gateway_dto.OwnerResponse;
import com_lab_microservices_pet_dto.CatListResponse;
import com_lab_microservices_pet_dto.CatResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис, который через RabbitMQ делает RPC-запросы
 * к микросервису Owner-Service (для получения информации о владельце)
 * и к Pet-Service (для получения списка котов владельца).
 */
@Service
public class OwnerGatewayService {

    private final RabbitTemplate rabbitTemplate;
    private final CatMapper catMapper;

    public OwnerGatewayService(RabbitTemplate rabbitTemplate, CatMapper catMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.catMapper = catMapper;
    }

    /**
     * Получить OwnerDTO с именем владельца и списком имен его котов:
     * 1) RPC "owner.get" → OwnerResponse(id, name)
     * 2) RPC "pet.byOwner" → CatListResponse (List<CatResponse>)
     * 3) Собираем List<String> catNames и возвращаем OwnerDTO.
     */
    public OwnerDTO getOwnerWithCats(Long ownerId) {
        // 1) RPC к Owner-Service
        OwnerRequest ownerRequest = new OwnerRequest(ownerId);
        OwnerResponse ownerResponse = (OwnerResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_OWNER_GET, ownerRequest);
        if (ownerResponse == null) {
            throw new RuntimeException("Owner-Service did not respond for ownerId = " + ownerId);
        }

        // 2) RPC к Pet-Service: получаем всех котов по ownerId
        @SuppressWarnings("unchecked")
        CatListResponse catListResponse = (CatListResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_BY_OWNER, ownerId);

        List<String> catNames;
        if (catListResponse == null || catListResponse.getCats() == null) {
            catNames = List.of();
        } else {
            List<CatResponse> catResponses = catListResponse.getCats();
            catNames = catResponses.stream()
                    .map(CatResponse::getName)
                    .collect(Collectors.toList());
        }

        // 3) Собираем и возвращаем OwnerDTO
        return new OwnerDTO(ownerResponse.getId(), ownerResponse.getName(), catNames);
    }

    /**
     * Создать новую запись о владельце в микросервисе Owner-Service
     * (при регистрации).
     */
    public void createOwnerInOwnerService(Long ownerId, String name) {
        OwnerCreateRequest createRequest = new OwnerCreateRequest(ownerId, name);
        rabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_OWNER_CREATE, createRequest);
    }
}
