package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: запрос на создание кота (name, color, ownerId).
 */
public class CatCreateRequest implements Serializable {
    private String name;
    private String color;
    private Long ownerId;

    public CatCreateRequest() {
    }

    public CatCreateRequest(String name, String color, Long ownerId) {
        this.name = name;
        this.color = color;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
