package com.app_with_database.service;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.model.Cat;
import com.app_with_database.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {

    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public CatDTO getCat(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow();
        return new CatDTO(cat.getId(), cat.getName(), cat.getColor());
    }

    public List<CatDTO> getCatsByColor(String color, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Cat> cats = catRepository.findByColor(color, pageable);
        return cats.stream().map(cat -> new CatDTO(cat.getId(), cat.getName(), cat.getColor())).collect(Collectors.toList());
    }

    public CatDTO createCat(CatDTO catDTO) {
        Cat cat = new Cat();
        cat.setName(catDTO.getName());
        cat.setColor(catDTO.getColor());
        Cat savedCat = catRepository.save(cat);
        return new CatDTO(savedCat.getId(), savedCat.getName(), savedCat.getColor());
    }

    public CatDTO updateCat(Long id, CatDTO catDTO) {
        Cat cat = catRepository.findById(id).orElseThrow();
        cat.setName(catDTO.getName());
        cat.setColor(catDTO.getColor());
        Cat updatedCat = catRepository.save(cat);
        return new CatDTO(updatedCat.getId(), updatedCat.getName(), updatedCat.getColor());
    }

    public void deleteCat(Long id) {
        catRepository.deleteById(id);
    }
}