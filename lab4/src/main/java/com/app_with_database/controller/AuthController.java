package com.app_with_database.controller;

import com.app_with_database.dto.*;
import com.app_with_database.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final OwnerService ownerService;

    @Autowired
    public AuthController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationDTO dto) {
        UserDTO created = ownerService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    // /login и /logout — авт. Spring Security
}
