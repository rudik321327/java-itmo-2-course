package com.lab.microservices.pet.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO: обёртка для списка CatResponse.
 */
public class CatListResponse implements Serializable {
    private List<CatResponse> cats;

    public CatListResponse() {
    }

    public CatListResponse(List<CatResponse> cats) {
        this.cats = cats;
    }

    public List<CatResponse> getCats() {
        return cats;
    }

    public void setCats(List<CatResponse> cats) {
        this.cats = cats;
    }
}
