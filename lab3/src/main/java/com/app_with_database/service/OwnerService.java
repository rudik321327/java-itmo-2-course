package com.app_with_database.service;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.model.Owner;
import com.app_with_database.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerDTO getOwner(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow();
        List<String> catNames = owner.getCats().stream()
                .map(cat -> cat.getName())
                .collect(Collectors.toList());
        return new OwnerDTO(owner.getId(), owner.getName(), catNames);
    }
}
