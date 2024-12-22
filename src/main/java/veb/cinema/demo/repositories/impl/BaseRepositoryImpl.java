package veb.cinema.demo.repositories.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import veb.cinema.demo.models.BaseEntity;
import veb.cinema.demo.repositories.generic.CreateRepository;
import veb.cinema.demo.repositories.generic.DeleteRepository;
import veb.cinema.demo.repositories.generic.ReadRepository;
import veb.cinema.demo.repositories.generic.UpdateRepository;

public class BaseRepositoryImpl<T extends BaseEntity> implements ReadRepository<T>, CreateRepository<T>, UpdateRepository<T>, DeleteRepository<T>{
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public BaseRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Transactional
    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Transactional
    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    protected EntityManager getEntityManger() {
        return entityManager;
    }

    protected Class<T> getEntityClass(){
        return entityClass;
    }

    @Transactional
    @Override
    public void delete(T entity) {
        if (entity != null && entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Optional<T> entity = findById(id);
        entity.ifPresent(this::delete);
    }
}

