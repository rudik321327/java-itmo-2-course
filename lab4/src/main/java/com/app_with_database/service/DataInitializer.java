package com.app_with_database.service;

import com.app_with_database.model.Owner;
import com.app_with_database.model.Role;
import com.app_with_database.repository.OwnerRepository;
import com.app_with_database.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
    private final RoleRepository roleRepo;
    private final OwnerRepository ownerRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepo,
                           OwnerRepository ownerRepo,
                           PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.ownerRepo = ownerRepo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        Role user = roleRepo.save(new Role(null, "ROLE_USER"));
        Role admin = roleRepo.save(new Role(null, "ROLE_ADMIN"));

        if (ownerRepo.findByUsername("admin").isEmpty()) {
            Owner a = new Owner();
            a.setUsername("admin");
            a.setPassword(encoder.encode("admin"));
            a.setName("Administrator");
            a.getRoles().add(admin);
            ownerRepo.save(a);
        }
    }
}
