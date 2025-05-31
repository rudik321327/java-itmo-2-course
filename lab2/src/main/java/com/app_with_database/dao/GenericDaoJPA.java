package com.app_with_database.dao;

import com.app_with_database.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDaoJPA<T> implements GenericDao<T> {
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public GenericDaoJPA() {
        this.clazz = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(long id) {
        T e = getById(id);
        if (e != null) deleteByEntity(e);
    }

    @Override
    public void deleteByEntity(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAll() {
        getAll().forEach(this::deleteByEntity);
    }

    @Override
    public T getById(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(clazz, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
