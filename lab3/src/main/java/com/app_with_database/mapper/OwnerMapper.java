package com.app_with_database.mapper;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.model.Owner;
import com.app_with_database.model.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    // Преобразование сущности Owner в DTO OwnerDTO
    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "cats", target = "cats")
    })
    OwnerDTO ownerToOwnerDTO(Owner owner);

    // Метод для преобразования списка котов в список их имен
    default List<String> mapCatsToNames(List<Cat> cats) {
        if (cats == null) {
            return null;
        }
        return cats.stream()
                .map(Cat::getName)  // Преобразование каждого кота в его имя
                .collect(Collectors.toList());
    }
}
