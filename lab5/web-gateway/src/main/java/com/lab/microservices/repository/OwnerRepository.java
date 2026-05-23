package com.lab.microservices.gateway.repository;

import com.lab.microservices.gateway.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для Entity Owner.
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByUsername(String username);
}
