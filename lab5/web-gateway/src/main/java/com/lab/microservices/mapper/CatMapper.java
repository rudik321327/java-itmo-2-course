package com.lab.microservices.gateway.mapper;

import com.lab.microservices.gateway.dto.CatDTO;
import com.lab.microservices.pet.dto.CatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct-маппер для конвертации CatResponse (из Pet-Service) → CatDTO.
 */
@Mapper(componentModel = "spring")
public interface CatMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "color", target = "color")
    CatDTO catResponseToCatDTO(CatResponse catResponse);
}
