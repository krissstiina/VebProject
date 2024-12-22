package veb.cinema.demo.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.Hall;
import veb.cinema.demo.repositories.HallRepository;

@Repository
public class HallRepositoryImpl extends BaseRepositoryImpl<Hall> implements HallRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public HallRepositoryImpl() {
        super(Hall.class);
    }


}
