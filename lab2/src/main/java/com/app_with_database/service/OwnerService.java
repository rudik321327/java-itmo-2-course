package com.app_with_database.service;

import com.app_with_database.dao.OwnerDao;
import com.app_with_database.model.Owner;
import java.util.List;
import java.util.Objects;

public class OwnerService {
    private final OwnerDao dao = new OwnerDao();

    public Owner create(Owner o) {
        validate(o);
        return dao.save(o);
    }

    public Owner update(Owner o) {
        Objects.requireNonNull(o.getId(), "ID для обновления обязателен");
        validate(o);
        return dao.update(o);
    }

    public void deleteById(long id) { dao.deleteById(id); }
    public Owner getById(long id) { return dao.getById(id); }
    public List<Owner> getAll() { return dao.getAll(); }

    private void validate(Owner o) {
        if (o.getName() == null || o.getName().isBlank())
            throw new IllegalArgumentException("Имя владельца обязательно");
        if (o.getBirthDate() == null)
            throw new IllegalArgumentException("Дата рождения обязательна");
    }
}
