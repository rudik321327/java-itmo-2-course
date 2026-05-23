package com.lab.microservices.gateway.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

/**
 * Entity: Role (роль пользователя). Содержит id, name.
 * @JsonValue на getName() означает, что при сериализации в JSON
 * объекта Role выдаётся просто строка name (например, "ROLE_USER").
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g. "ROLE_USER", "ROLE_ADMIN"

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @JsonValue
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
