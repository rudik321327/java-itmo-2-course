package com.app_with_database.mapper;

import com.app_with_database.dto.OwnerDTO;
import com.app_with_database.model.Owner;
import com.app_with_database.model.Cat;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    @Mapping(source = "cats", target = "cats")
    OwnerDTO ownerToOwnerDTO(Owner owner);

    default List<String> map(List<Cat> cats) {
        return cats.stream().map(Cat::getName).collect(Collectors.toList());
    }
}
