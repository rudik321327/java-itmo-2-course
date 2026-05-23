package com.lab.microservices.owner.repository;

import com.lab.microservices.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с Entity Owner.
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // Базовых методов JpaRepository достаточно: save, findById, delete и т.д.
}
