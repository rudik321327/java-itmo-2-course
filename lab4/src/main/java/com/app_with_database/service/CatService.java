package com.app_with_database.service;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.mapper.CatMapper;
import com.app_with_database.model.Cat;
import com.app_with_database.model.Owner;
import com.app_with_database.repository.CatRepository;
import com.app_with_database.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {

    private final CatRepository   catRepo;
    private final OwnerRepository ownerRepo;
    private final CatMapper       mapper;

    @Autowired
    public CatService(CatRepository catRepo, OwnerRepository ownerRepo, CatMapper mapper) {
        this.catRepo   = catRepo;
        this.ownerRepo = ownerRepo;
        this.mapper    = mapper;
    }

    public CatDTO getCat(Long id) {
        return catRepo.findById(id)
                .map(mapper::catToCatDTO)
                .orElseThrow();
    }

    public List<CatDTO> getCatsByColor(String color, int page, int size) {
        return catRepo.findByColor(color, PageRequest.of(page, size))
                .stream()
                .map(mapper::catToCatDTO)
                .collect(Collectors.toList());
    }

    public CatDTO createForCurrentUser(CatDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Owner owner = ownerRepo.findByUsername(username).orElseThrow();

        Cat cat = new Cat();
        cat.setName(dto.getName());
        cat.setColor(dto.getColor());
        cat.setOwner(owner);

        return mapper.catToCatDTO(catRepo.save(cat));
    }

    public CatDTO update(Long id, CatDTO dto) {
        Cat cat = catRepo.findById(id).orElseThrow();
        cat.setName(dto.getName());
        cat.setColor(dto.getColor());
        return mapper.catToCatDTO(catRepo.save(cat));
    }

    public void delete(Long id) {
        catRepo.deleteById(id);
    }
}
