package com.app_with_database.controller;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDTO> getCat(@PathVariable Long id) {
        CatDTO cat = catService.getCat(id);
        return ResponseEntity.ok(cat);
    }

    @GetMapping
    public ResponseEntity<List<CatDTO>> getCats(@RequestParam String color, @RequestParam int page, @RequestParam int size) {
        List<CatDTO> cats = catService.getCatsByColor(color, page, size);
        return ResponseEntity.ok(cats);
    }
}
