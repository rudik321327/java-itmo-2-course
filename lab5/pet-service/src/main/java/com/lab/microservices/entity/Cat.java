package com.lab.microservices.pet.entity;

import jakarta.persistence.*;

/**
 * Entity: Cat (кот). Храним id (автогенерируется), name, color и external ownerId.
 */
@Entity
@Table(name = "cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    /**
     * Ссылка на идентификатор владельца (Owner ID из Web-Gateway DB).
     */
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    public Cat() {
    }

    public Cat(String name, String color, Long ownerId) {
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
