package veb.cinema.demo.repositories;

import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.Films;
import veb.cinema.demo.models.Reviews;
import veb.cinema.demo.repositories.generic.CreateRepository;
import veb.cinema.demo.repositories.generic.DeleteRepository;
import veb.cinema.demo.repositories.generic.ReadRepository;
import veb.cinema.demo.repositories.generic.UpdateRepository;

import java.util.List;

@Repository
public interface ReviewsRepository extends ReadRepository<Reviews>, CreateRepository<Reviews>, UpdateRepository<Reviews>, DeleteRepository<Reviews> {
    List<Reviews> findAllByFilmId(Integer filmId);
    List<Reviews> findByFilm(Films film);
}
