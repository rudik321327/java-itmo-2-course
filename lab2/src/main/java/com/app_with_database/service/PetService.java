package com.app_with_database.service;

import com.app_with_database.dao.PetDao;
import com.app_with_database.model.Pet;
import java.util.List;
import java.util.Objects;

public class PetService {
    private final PetDao dao = new PetDao();

    public Pet create(Pet p) {
        validate(p);
        return dao.save(p);
    }

    public Pet update(Pet p) {
        Objects.requireNonNull(p.getId(), "ID для обновления обязателен");
        validate(p);
        return dao.update(p);
    }

    public void deleteById(long id) { dao.deleteById(id); }
    public Pet getById(long id) { return dao.getById(id); }
    public List<Pet> getAll() { return dao.getAll(); }

    private void validate(Pet p) {
        if (p.getName() == null || p.getName().isBlank())
            throw new IllegalArgumentException("Имя питомца обязательно");
        if (p.getBirthDate() == null)
            throw new IllegalArgumentException("Дата рождения обязательна");
    }
}
