package com.lab.microservices.gateway.security;

import com.lab.microservices.gateway.model.Owner;
import com.lab.microservices.gateway.repository.OwnerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Загрузка пользователя (UserDetails) из Web-Gateway DB при аутентификации.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public CustomUserDetailsService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(
                owner.getUsername(),
                owner.getPassword(),
                owner.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
