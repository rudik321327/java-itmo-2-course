package com.lab.microservices.gateway.service;

import com.lab.microservices.gateway.dto.CatDTO;
import com_lab_microservices_gateway_mapper.CatMapper;
import com_lab_microservices_gateway_config.RabbitConfig;
import com_lab_microservices_pet_dto.CatCreateRequest;
import com_lab_microservices_pet_dto.CatDeleteRequest;
import com_lab_microservices_pet_dto.CatDeleteResponse;
import com_lab_microservices_pet_dto.CatGetRequest;
import com_lab_microservices_pet_dto.CatListRequest;
import com_lab_microservices_pet_dto.CatListResponse;
import com_lab_microservices_pet_dto.CatResponse;
import com_lab_microservices_pet_dto.CatUpdateRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис, который через RabbitMQ делает RPC-запросы
 * к микросервису Pet-Service (CRUD для котов).
 */
@Service
public class CatGatewayService {

    private final RabbitTemplate rabbitTemplate;
    private final CatMapper catMapper;

    public CatGatewayService(RabbitTemplate rabbitTemplate, CatMapper catMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.catMapper = catMapper;
    }

    /**
     * GET /api/cats/{id}
     */
    public CatDTO getCat(Long catId) {
        CatGetRequest request = new CatGetRequest(catId);
        CatResponse response = (CatResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_GET, request);
        if (response == null) {
            throw new RuntimeException("Pet-Service did not respond for catId = " + catId);
        }
        return catMapper.catResponseToCatDTO(response);
    }

    /**
     * GET /api/cats?color=X&page=P&size=S
     */
    public List<CatDTO> getCatsByColor(String color, int page, int size) {
        CatListRequest request = new CatListRequest(color, page, size);
        CatListResponse response = (CatListResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_LIST, request);
        if (response == null || response.getCats() == null) {
            return List.of();
        }
        return response.getCats().stream()
                .map(catMapper::catResponseToCatDTO)
                .collect(Collectors.toList());
    }

    /**
     * POST /api/cats
     * Принимаем CatDTO (без id), ownerId передаём отдельно.
     */
    public CatDTO createCat(CatDTO dto, Long ownerId) {
        CatCreateRequest request = new CatCreateRequest(dto.getName(), dto.getColor(), ownerId);
        CatResponse response = (CatResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_CREATE, request);
        if (response == null) {
            throw new RuntimeException("Pet-Service did not respond on cat.create");
        }
        return catMapper.catResponseToCatDTO(response);
    }

    /**
     * PUT /api/cats/{id}
     */
    public CatDTO updateCat(Long catId, CatDTO dto) {
        CatUpdateRequest request = new CatUpdateRequest(catId, dto.getName(), dto.getColor());
        CatResponse response = (CatResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_UPDATE, request);
        if (response == null) {
            throw new RuntimeException("Pet-Service did not respond on cat.update");
        }
        return catMapper.catResponseToCatDTO(response);
    }

    /**
     * DELETE /api/cats/{id}
     */
    public void deleteCat(Long catId) {
        CatDeleteRequest request = new CatDeleteRequest(catId);
        CatDeleteResponse response = (CatDeleteResponse) rabbitTemplate
                .convertSendAndReceive(RabbitConfig.QUEUE_PET_DELETE, request);
        if (response == null || !response.isSuccess()) {
            throw new RuntimeException("Failed to delete cat with id=" + catId);
        }
    }
}
