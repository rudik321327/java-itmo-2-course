package com.app_with_database.service;

import com.app_with_database.repository.CatRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {
    private final CatRepository catRepo;
    public SecurityService(CatRepository catRepo) {
        this.catRepo = catRepo;
    }

    public boolean isOwnerOfCat(Long catId) {
        return catRepo.findById(catId)
                .map(cat -> cat.getOwner().getUsername()
                        .equals(SecurityContextHolder.getContext()
                                .getAuthentication().getName()))
                .orElse(false);
    }
}
