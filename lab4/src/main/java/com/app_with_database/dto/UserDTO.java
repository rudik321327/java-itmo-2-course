package com.app_with_database.dto;

import com.app_with_database.model.Role;   // <-- новый импорт
import java.util.Set;

public class UserDTO {

    private Long id;
    private String username;
    private String name;

    // ⚠  Меняем тип на Set<Role>
    private Set<Role> roles;

    public UserDTO() {}

    public UserDTO(Long id, String username, String name, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }

    // геттеры/сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
