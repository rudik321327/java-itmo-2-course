package com.app_with_database.dto;

import java.util.List;

public class OwnerDTO {
    private Long id;
    private String name;
    private List<String> cats;  // Список строк с именами котов

    // Конструктор, геттеры и сеттеры
    public OwnerDTO(Long id, String name, List<String> cats) {
        this.id = id;
        this.name = name;
        this.cats = cats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCats() {
        return cats;
    }

    public void setCats(List<String> cats) {
        this.cats = cats;
    }
}
