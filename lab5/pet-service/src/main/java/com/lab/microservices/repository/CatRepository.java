package com.lab.microservices.pet.repository;

import com.lab.microservices.pet.entity.Cat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с Entity Cat.
 */
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByColor(String color, Pageable pageable);
    List<Cat> findByOwnerId(Long ownerId);
}
