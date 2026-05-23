package com.lab.microservices.pet.dto;

import java.io.Serializable;

/**
 * DTO: запрос на получение списка котов по цвету + пагинация.
 */
public class CatListRequest implements Serializable {
    private String color;
    private int page;
    private int size;

    public CatListRequest() {
    }

    public CatListRequest(String color, int page, int size) {
        this.color = color;
        this.page = page;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
