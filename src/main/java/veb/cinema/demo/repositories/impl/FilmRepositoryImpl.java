package veb.cinema.demo.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.Films;
import veb.cinema.demo.repositories.FilmRepository;

import java.util.List;

@Repository
public class FilmRepositoryImpl extends BaseRepositoryImpl<Films> implements FilmRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public FilmRepositoryImpl() {
        super(Films.class);
    }

    @Override
    public Page<Films> findAll(Pageable pageable) {
        List<Films> films = entityManager.createQuery("FROM Films f", Films.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(f) FROM Films f", Long.class)
                .getSingleResult();

        return new PageImpl<>(films, pageable, total);
    }

    @Override
    public List<Films> findByGenres(String genresPurchased) {
        return entityManager.createQuery("SELECT f FROM Films f WHERE f.genre IN :genres", Films.class)
                .setParameter("genres", genresPurchased)
                .getResultList();
    }

}
