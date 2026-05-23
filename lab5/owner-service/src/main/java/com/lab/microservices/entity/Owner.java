package com.lab.microservices.owner.entity;

import jakarta.persistence.*;

/**
 * Entity: владелец (Owner). Храним только id и name.
 * ID задаётся извне (Web-Gateway), поэтому без @GeneratedValue.
 */
@Entity
@Table(name = "owner")
public class Owner {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    public Owner() {
    }

    public Owner(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
