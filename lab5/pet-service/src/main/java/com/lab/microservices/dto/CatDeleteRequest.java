package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: запрос на удаление кота по ID.
 */
public class CatDeleteRequest implements Serializable {
    private Long id;

    public CatDeleteRequest() {
    }

    public CatDeleteRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
