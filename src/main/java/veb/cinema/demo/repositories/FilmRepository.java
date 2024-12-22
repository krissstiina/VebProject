package veb.cinema.demo.repositories;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.Films;
import veb.cinema.demo.models.Reviews;
import veb.cinema.demo.repositories.generic.CreateRepository;
import veb.cinema.demo.repositories.generic.ReadRepository;
import veb.cinema.demo.repositories.generic.UpdateRepository;

import java.util.List;

@Repository
public interface FilmRepository extends ReadRepository<Films>, CreateRepository<Films>, UpdateRepository<Films> {
    Page<Films> findAll(Pageable pageable);
    List<Films> findByGenres(String genresPurchased);
}
