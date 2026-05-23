package com.lab.microservices.pet.listener;

import com.lab.microservices.pet.config.RabbitConfig;
import com.lab.microservices.pet.dto.*;
import com.lab.microservices.pet.service.CatService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RabbitMQ Listener для Pet-Service.
 */
@Component
public class CatListener {

    private final CatService catService;

    public CatListener(CatService catService) {
        this.catService = catService;
    }

    /**
     * GET cat by ID.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_CAT_GET)
    public CatResponse handleGetCat(CatGetRequest request) {
        return catService.getCat(request);
    }

    /**
     * LIST cats by color + pagination.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_CAT_LIST)
    public CatListResponse handleListCats(CatListRequest request) {
        return catService.listCats(request);
    }

    /**
     * CREATE new cat.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_CAT_CREATE)
    public CatResponse handleCreateCat(CatCreateRequest request) {
        return catService.createCat(request);
    }

    /**
     * UPDATE cat by ID.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_CAT_UPDATE)
    public CatResponse handleUpdateCat(CatUpdateRequest request) {
        return catService.updateCat(request);
    }

    /**
     * DELETE cat by ID.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_CAT_DELETE)
    public CatDeleteResponse handleDeleteCat(CatDeleteRequest request) {
        return catService.deleteCat(request);
    }

    /**
     * GET all cats by ownerId.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_CAT_BY_OWNER)
    public CatListResponse handleGetCatsByOwner(Long ownerId) {
        List<CatResponse> list = catService.getCatsByOwnerId(ownerId);
        return new CatListResponse(list);
    }
}
