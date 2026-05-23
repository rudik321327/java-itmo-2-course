package com.app_with_database.controller;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.service.CatService;
import com.app_with_database.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    private final CatService catService;
    private final SecurityService secService;

    @Autowired
    public CatController(CatService catService, SecurityService secService) {
        this.catService = catService;
        this.secService = secService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDTO> getCat(@PathVariable Long id) {
        return ResponseEntity.ok(catService.getCat(id));
    }

    @GetMapping
    public ResponseEntity<List<CatDTO>> getCats(
            @RequestParam String color,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(catService.getCatsByColor(color,page,size));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<CatDTO> create(@RequestBody CatDTO dto) {
        CatDTO saved = catService.createForCurrentUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfCat(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<CatDTO> update(@PathVariable Long id, @RequestBody CatDTO dto) {
        return ResponseEntity.ok(catService.update(id,dto));
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwnerOfCat(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        catService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
