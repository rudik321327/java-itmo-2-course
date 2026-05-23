package com.lab.microservices.pet.service;

import com.lab.microservices.pet.dto.*;
import com.lab.microservices.pet.entity.Cat;
import com.lab.microservices.pet.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис, обрабатывающий бизнес-логику микросервиса Pet-Service.
 */
@Service
public class CatService {

    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * Создание нового кота (с передачей ownerId).
     */
    public CatResponse createCat(CatCreateRequest request) {
        Cat cat = new Cat(request.getName(), request.getColor(), request.getOwnerId());
        Cat saved = catRepository.save(cat);
        return new CatResponse(saved.getId(), saved.getName(), saved.getColor(), saved.getOwnerId());
    }

    /**
     * Получение кота по ID.
     */
    public CatResponse getCat(CatGetRequest request) {
        Cat cat = catRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Cat with id = " + request.getId() + " not found"));
        return new CatResponse(cat.getId(), cat.getName(), cat.getColor(), cat.getOwnerId());
    }

    /**
     * Получение списка котов по цвету + пагинация.
     */
    public CatListResponse listCats(CatListRequest request) {
        List<Cat> cats = catRepository.findByColor(
                request.getColor(),
                PageRequest.of(request.getPage(), request.getSize())
        );
        List<CatResponse> responses = cats.stream()
                .map(c -> new CatResponse(c.getId(), c.getName(), c.getColor(), c.getOwnerId()))
                .collect(Collectors.toList());
        return new CatListResponse(responses);
    }

    /**
     * Обновление кота по ID (name, color).
     */
    public CatResponse updateCat(CatUpdateRequest request) {
        Cat existing = catRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Cat with id = " + request.getId() + " not found"));
        existing.setName(request.getName());
        existing.setColor(request.getColor());
        Cat saved = catRepository.save(existing);
        return new CatResponse(saved.getId(), saved.getName(), saved.getColor(), saved.getOwnerId());
    }

    /**
     * Удаление кота по ID.
     */
    public CatDeleteResponse deleteCat(CatDeleteRequest request) {
        if (!catRepository.existsById(request.getId())) {
            return new CatDeleteResponse(false);
        }
        catRepository.deleteById(request.getId());
        return new CatDeleteResponse(true);
    }

    /**
     * Получение списка всех котов конкретного владельца.
     */
    public List<CatResponse> getCatsByOwnerId(Long ownerId) {
        List<Cat> cats = catRepository.findByOwnerId(ownerId);
        return cats.stream()
                .map(c -> new CatResponse(c.getId(), c.getName(), c.getColor(), c.getOwnerId()))
                .collect(Collectors.toList());
    }
}
