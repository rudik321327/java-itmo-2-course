package com.app_with_database.service;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.dto.RegistrationDTO;
import com.app_with_database.dto.UserDTO;
import com.app_with_database.mapper.OwnerMapper;
import com.app_with_database.mapper.UserMapper;
import com.app_with_database.model.Owner;
import com.app_with_database.repository.OwnerRepository;
import com.app_with_database.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private final OwnerRepository repo;
    private final RoleRepository  roleRepo;
    private final PasswordEncoder encoder;
    private final OwnerMapper     ownerMapper;
    private final UserMapper      userMapper;

    @Autowired
    public OwnerService(OwnerRepository repo,
                        RoleRepository  roleRepo,
                        PasswordEncoder encoder,
                        OwnerMapper     ownerMapper,
                        UserMapper      userMapper) {
        this.repo        = repo;
        this.roleRepo    = roleRepo;
        this.encoder     = encoder;
        this.ownerMapper = ownerMapper;
        this.userMapper  = userMapper;
    }

    public OwnerDTO getOwner(Long id) {
        Owner o = repo.findById(id).orElseThrow();
        return ownerMapper.ownerToOwnerDTO(o);
    }

    public UserDTO register(RegistrationDTO dto) {

        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username занято");
        }

        Owner owner = new Owner();
        owner.setUsername(dto.getUsername());
        owner.setPassword(encoder.encode(dto.getPassword()));
        owner.setName(dto.getName());
        owner.getRoles().add(
                roleRepo.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Role ROLE_USER не найдена"))
        );

        return userMapper.ownerToUserDTO(repo.save(owner));
    }
}
