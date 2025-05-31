package com.lab.microservices.owner.dto;

import java.io.Serializable;

/**
 * DTO: запрос на получение информации о владельце по ID.
 */
public class OwnerRequest implements Serializable {
    private Long id;

    public OwnerRequest() {
    }

    public OwnerRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
