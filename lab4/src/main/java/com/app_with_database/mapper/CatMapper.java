package com.app_with_database.mapper;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.model.Cat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatMapper {
    CatDTO catToCatDTO(Cat cat);
}
