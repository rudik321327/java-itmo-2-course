package com.app_with_database.mapper;

import com.app_with_database.dto.UserDTO;
import com.app_with_database.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source="username", target="username")
    @Mapping(source="name", target="name")
    @Mapping(source="roles", target="roles")
    UserDTO ownerToUserDTO(Owner owner);
}
