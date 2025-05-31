package com.app_with_database.dao;

import java.util.List;

public interface GenericDao<T> {
    T save(T entity);
    T update(T entity);
    void deleteById(long id);
    void deleteByEntity(T entity);
    void deleteAll();
    T getById(long id);
    List<T> getAll();
}
