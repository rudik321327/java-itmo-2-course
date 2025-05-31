package com.lab.microservices.gateway.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity: владелец (owner) для Web-Gateway DB.
 * Храним id, username, password, name, roles.
 */
@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // кодируем через BCrypt

    @Column(nullable = false)
    private String name;     // отображаемое имя

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "owner_roles",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Owner() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
