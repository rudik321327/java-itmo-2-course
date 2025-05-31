package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: запрос на получение кота по ID.
 */
public class CatGetRequest implements Serializable {
    private Long id;

    public CatGetRequest() {
    }

    public CatGetRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
