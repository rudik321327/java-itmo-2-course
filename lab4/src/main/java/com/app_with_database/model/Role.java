package com.app_with_database.model;

import com.fasterxml.jackson.annotation.JsonValue;   // новый импорт
import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role() { }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // геттеры/сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    /**
     *  Jackson будет сериализовать объект Role
     *  как простую строку — имя роли.
     */
    @JsonValue
    public String getName() {          // ←--- аннотация @JsonValue
        return name;
    }
    public void setName(String name) { this.name = name; }
}
