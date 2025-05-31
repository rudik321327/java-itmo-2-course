package com.lab.microservices.gateway.mapper;

import com.lab.microservices.gateway.dto.OwnerDTO;
import org.mapstruct.Mapper;

/**
 * OwnerMapper остаётся пустым, потому что мы вручную собираем OwnerDTO
 * из результатов двух RPC-запросов (OwnerResponse + список CatResponse).
 */
@Mapper(componentModel = "spring")
public interface OwnerMapper {
    // Если бы было Entity Owner → OwnerDTO, тут бы был метод.
}
