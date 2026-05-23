package com.lab.microservices.gateway.controller;

import com_lab_microservices_gateway_dto.OwnerDTO;
import com_lab_microservices_gateway_service.OwnerGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для операций с владельцами: /api/owners/{id}
 * Возвращает OwnerDTO (id, name, List<String> cats).
 */
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerGatewayService ownerGatewayService;

    @Autowired
    public OwnerController(OwnerGatewayService ownerGatewayService) {
        this.ownerGatewayService = ownerGatewayService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long id) {
        OwnerDTO ownerDTO = ownerGatewayService.getOwnerWithCats(id);
        return ResponseEntity.ok(ownerDTO);
    }
}
