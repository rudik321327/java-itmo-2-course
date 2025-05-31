package com.app_with_database.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String breed;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany
    @JoinTable(name = "pet_friends",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Pet> friends;

    // Конструкторы, геттеры и сеттеры
    public Pet() {}
    public Long getId() { return id; }
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setBirthDate(LocalDate bd) { this.birthDate = bd; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBreed(String breed) { this.breed = breed; }
    public String getBreed() { return breed; }
    public void setColor(Color color) { this.color = color; }
    public Color getColor() { return color; }
    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
    public List<Pet> getFriends() { return friends; }
    public void setFriends(List<Pet> friends) { this.friends = friends; }
}
