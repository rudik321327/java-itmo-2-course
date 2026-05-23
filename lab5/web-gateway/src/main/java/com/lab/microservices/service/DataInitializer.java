package com.lab.microservices.gateway.service;

import com.lab.microservices.gateway.model.Owner;
import com.lab.microservices.gateway.model.Role;
import com.lab.microservices.gateway.repository.OwnerRepository;
import com.lab.microservices.gateway.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * Инициализация ролей и администратора при старте Web-Gateway.
 */
@Component
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OwnerGatewayService ownerGatewayService;

    @Autowired
    public DataInitializer(RoleRepository roleRepository,
                           OwnerRepository ownerRepository,
                           PasswordEncoder passwordEncoder,
                           OwnerGatewayService ownerGatewayService) {
        this.roleRepository = roleRepository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownerGatewayService = ownerGatewayService;
    }

    @PostConstruct
    public void init() {
        // Создаём роли ROLE_USER и ROLE_ADMIN, если их нет
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

        // Создаём администратора (username=admin, password=admin) в Web-Gateway DB,
        // если такого пользователя ещё нет
        if (ownerRepository.findByUsername("admin").isEmpty()) {
            Owner admin = new Owner();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setName("Administrator");
            admin.getRoles().add(adminRole);
            admin.getRoles().add(userRole); // можно давать обе роли
            Owner saved = ownerRepository.save(admin);

            // Отправляем RPC в Owner-Service, чтобы там тоже появилась запись о владельце
            ownerGatewayService.createOwnerInOwnerService(saved.getId(), saved.getName());
        }
    }
}
