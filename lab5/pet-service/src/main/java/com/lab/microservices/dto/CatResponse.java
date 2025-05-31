package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: ответ о коте (id, name, color, ownerId).
 */
public class CatResponse implements Serializable {
    private Long id;
    private String name;
    private String color;
    private Long ownerId;

    public CatResponse() {
    }

    public CatResponse(Long id, String name, String color, Long ownerId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.ownerId = ownerId;
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

    public Long getOwnerId() {
        return ownerId;
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

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
