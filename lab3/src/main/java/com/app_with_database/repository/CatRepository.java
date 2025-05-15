package com.app_with_database.repository;

import com.app_with_database.model.Cat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CatRepository extends CrudRepository<Cat, Long> {
    List<Cat> findByColor(String color, Pageable pageable);  // Фильтрация по цвету с пагинацией
}
