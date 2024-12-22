package veb.cinema.demo.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.Films;
import veb.cinema.demo.models.Reviews;
import veb.cinema.demo.repositories.ReviewsRepository;

import java.util.List;

@Repository
public class ReviewsRepositoryImpl extends BaseRepositoryImpl<Reviews> implements ReviewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ReviewsRepositoryImpl() {
        super(Reviews.class);
    }

    @Override
    public List<Reviews> findByFilm(Films film) {
        return entityManager.createQuery("SELECT r FROM Reviews r WHERE r.film = :film", Reviews.class)
                .setParameter("film", film)
                .getResultList();
    }

    @Override
    public List<Reviews> findAllByFilmId(Integer filmId) {
        return entityManager.createQuery(
                        "SELECT r FROM Reviews r JOIN r.film f WHERE f.id = :filmId", Reviews.class)
                .setParameter("filmId", filmId)
                .getResultList();
    }
}
