package com.lab.microservices.gateway.controller;

import com.lab.microservices.gateway.dto.CatDTO;
import com_lab_microservices_gateway_service.CatGatewayService;
import com_lab_microservices_gateway_service.SecurityService;
import com_lab_microservices_gateway_repository.OwnerRepository;
import com_lab_microservices_gateway_model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для операций с котами: /api/cats/**
 * Взаимодействие через CatGatewayService (RPC в Pet-Service).
 */
@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatGatewayService catGatewayService;
    private final SecurityService securityService;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CatController(CatGatewayService catGatewayService,
                         SecurityService securityService,
                         OwnerRepository ownerRepository) {
        this.catGatewayService = catGatewayService;
        this.securityService = securityService;
        this.ownerRepository = ownerRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDTO> getCat(@PathVariable Long id) {
        CatDTO dto = catGatewayService.getCat(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CatDTO>> getCats(
            @RequestParam String color,
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<CatDTO> cats = catGatewayService.getCatsByColor(color, page, size);
        return ResponseEntity.ok(cats);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<CatDTO> create(@RequestBody CatDTO dto, Authentication authentication) {
        // Находим ownerId текущего пользователя:
        String username = authentication.getName();
        Owner owner = ownerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long ownerId = owner.getId();

        CatDTO created = catGatewayService.createCat(dto, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfCat(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<CatDTO> update(@PathVariable Long id, @RequestBody CatDTO dto) {
        CatDTO updated = catGatewayService.updateCat(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfCat(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        catGatewayService.deleteCat(id);
        return ResponseEntity.noContent().build();
    }
}
