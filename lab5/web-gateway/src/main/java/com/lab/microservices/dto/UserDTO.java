package com.lab.microservices.gateway.dto;

import com.lab.microservices.gateway.model.Role;
import java.util.Set;

/**
 * DTO: для возвращения данных пользователя при регистрации.
 * Содержит id, username, name, roles.
 */
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private Set<Role> roles;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String name, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
