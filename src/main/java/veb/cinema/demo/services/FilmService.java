package veb.cinema.demo.services;

import java.util.List;

import veb.cinema.demo.models.Films;
import veb.cinema.demo.dto.FilmDto;

public interface  FilmService {
    FilmDto findById(int id);
    List<FilmDto> findAll();
    int create(String name, String genre, String producer, int yearOdPublish,  double averageRating );
    void update(int id, String name, String genre, String producer, Integer yearOfPublish);
    String getFilmNameById(int id);
    List<FilmDto> getTop3FilmsByAverageRating();
    List<Films> findAllFilms();
}
