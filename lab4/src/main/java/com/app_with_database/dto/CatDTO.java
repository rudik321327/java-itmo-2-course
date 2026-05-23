package com.app_with_database.dto;

/**
 * DTO для кота — оставляем класс с обычными геттерами/сеттерами,
 * чтобы CatService спокойно вызывал getName()/getColor().
 */
public class CatDTO {

    private Long   id;
    private String name;
    private String color;

    public CatDTO() { }

    public CatDTO(Long id, String name, String color) {
        this.id    = id;
        this.name  = name;
        this.color = color;
    }

    public Long   getId()    { return id; }
    public void   setId(Long id) { this.id = id; }

    public String getName()  { return name; }
    public void   setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void   setColor(String color) { this.color = color; }
}
