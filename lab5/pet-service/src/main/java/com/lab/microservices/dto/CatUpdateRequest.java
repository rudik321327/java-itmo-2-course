package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: запрос на обновление кота по ID (id, name, color).
 */
public class CatUpdateRequest implements Serializable {
    private Long id;
    private String name;
    private String color;

    public CatUpdateRequest() {
    }

    public CatUpdateRequest(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
