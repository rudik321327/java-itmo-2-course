package com.lab.microservices.gateway.service;

import com.lab.microservices.gateway.dto.RegistrationDTO;
import com.lab.microservices.gateway.dto.UserDTO;
import com.lab.microservices.gateway.mapper.UserMapper;
import com.lab.microservices.gateway.model.Owner;
import com_lab_microservices_gateway_model.Role;
import com_lab_microservices_gateway_repository.OwnerRepository;
import com_lab_microservices_gateway_repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для регистрации новых пользователей (Owner) в Web-Gateway DB.
 * После сохранения мы отправляем RPC в Owner-Service для создания записи там.
 */
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OwnerGatewayService ownerGatewayService;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder,
                        UserMapper userMapper,
                        OwnerGatewayService ownerGatewayService) {
        this.ownerRepository = ownerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.ownerGatewayService = ownerGatewayService;
    }

    /**
     * Регистрация нового пользователя:
     * 1) Проверяем, что username свободен
     * 2) Создаём Owner entity, кодируем пароль, добавляем роль ROLE_USER
     *    и сохраняем в Web-Gateway DB
     * 3) Отправляем RPC "owner.create" в Owner-Service, чтобы там тоже создать запись
     * 4) Возвращаем UserDTO
     */
    public UserDTO register(RegistrationDTO dto) {
        if (ownerRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        Owner owner = new Owner();
        owner.setUsername(dto.getUsername());
        owner.setPassword(passwordEncoder.encode(dto.getPassword()));
        owner.setName(dto.getName());
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        owner.getRoles().add(userRole);
        Owner saved = ownerRepository.save(owner);

        // RPC: создаём Owner в микросервисе Owner-Service
        ownerGatewayService.createOwnerInOwnerService(saved.getId(), saved.getName());

        return userMapper.ownerToUserDTO(saved);
    }
}
