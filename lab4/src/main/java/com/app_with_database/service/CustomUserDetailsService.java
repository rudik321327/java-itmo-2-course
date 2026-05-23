package com.app_with_database.service;

import com.app_with_database.model.Owner;
import com.app_with_database.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final OwnerRepository repo;

    @Autowired
    public CustomUserDetailsService(OwnerRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Owner o = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(o.getUsername(), o.getPassword(),
                o.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .toList());
    }
}
