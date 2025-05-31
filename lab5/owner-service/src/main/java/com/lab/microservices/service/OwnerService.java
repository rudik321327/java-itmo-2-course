package com.lab.microservices.owner.service;

import com.lab.microservices.owner.dto.OwnerCreateRequest;
import com.lab.microservices.owner.dto.OwnerRequest;
import com.lab.microservices.owner.dto.OwnerResponse;
import com.lab.microservices.owner.entity.Owner;
import com.lab.microservices.owner.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис, обрабатывающий бизнес-логику микросервиса Owner-Service.
 */
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    /**
     * Обрабатывает запрос на создание владельца.
     */
    public OwnerResponse createOwner(OwnerCreateRequest request) {
        Owner owner = new Owner(request.getId(), request.getName());
        Owner saved = ownerRepository.save(owner);
        return new OwnerResponse(saved.getId(), saved.getName());
    }

    /**
     * Обрабатывает запрос на получение владельца по ID.
     */
    public OwnerResponse getOwner(OwnerRequest request) {
        Optional<Owner> maybe = ownerRepository.findById(request.getId());
        if (maybe.isEmpty()) {
            throw new RuntimeException("Owner with id = " + request.getId() + " not found");
        }
        Owner owner = maybe.get();
        return new OwnerResponse(owner.getId(), owner.getName());
    }
}
