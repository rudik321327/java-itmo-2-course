package com.lab.microservices.gateway.dto;

import java.util.List;

/**
 * DTO: для выдачи клиенту информации о владельце вместе со списком имен его котов.
 * id, name, cats (List<String>).
 */
public class OwnerDTO {
    private Long id;
    private String name;
    private List<String> cats;

    public OwnerDTO() {
    }

    public OwnerDTO(Long id, String name, List<String> cats) {
        this.id = id;
        this.name = name;
        this.cats = cats;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCats() {
        return cats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCats(List<String> cats) {
        this.cats = cats;
    }
}
