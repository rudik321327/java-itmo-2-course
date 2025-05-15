package com.app_with_database.mapper;

import com.app_with_database.dto.CatDTO;
import com.app_with_database.model.Cat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-15T18:01:51+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CatMapperImpl implements CatMapper {

    @Override
    public CatDTO catToCatDTO(Cat cat) {
        if ( cat == null ) {
            return null;
        }

        String name = null;
        String color = null;
        Long id = null;

        name = cat.getName();
        color = cat.getColor();
        id = cat.getId();

        CatDTO catDTO = new CatDTO( id, name, color );

        return catDTO;
    }
}
