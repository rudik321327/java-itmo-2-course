package com.lab.microservices.gateway.controller;

import com.lab.microservices.gateway.dto.LoginRequest;
import com_lab_microservices_gateway_dto.RegistrationDTO;
import com_lab_microservices_gateway_dto.UserDTO;
import com_lab_microservices_gateway_service.OwnerService;
import com_lab_microservices_gateway_security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для регистрации и логина (JWT).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OwnerService ownerService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(OwnerService ownerService,
                          AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider) {
        this.ownerService = ownerService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Регистрация нового пользователя: POST /api/auth/register
     * Возвращаем UserDTO.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationDTO dto) {
        UserDTO userDTO = ownerService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    /**
     * Логин: POST /api/auth/login
     * В теле запроса JSON { "username": "...", "password": "..." }.
     * Возвращаем в теле строку JWT (plain text).
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }
}
