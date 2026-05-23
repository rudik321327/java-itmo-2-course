package com.lab.microservices.gateway.mapper;

import com.lab.microservices.gateway.dto.UserDTO;
import com.lab.microservices.gateway.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct-маппер для конвертации Owner (Entity) → UserDTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "roles", target = "roles")
    UserDTO ownerToUserDTO(Owner owner);
}
