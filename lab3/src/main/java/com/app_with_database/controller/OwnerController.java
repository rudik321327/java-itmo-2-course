package com.app_with_database.controller;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long id) {
        OwnerDTO owner = ownerService.getOwner(id);
        return ResponseEntity.ok(owner);
    }
}
