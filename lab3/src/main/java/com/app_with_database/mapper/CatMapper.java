package com.app_with_database.mapper;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.model.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")  // Указываем, что маппер будет использоваться как Spring Bean
public interface CatMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "color", target = "color")
    CatDTO catToCatDTO(Cat cat);
}
