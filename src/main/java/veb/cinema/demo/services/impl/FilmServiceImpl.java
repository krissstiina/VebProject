package veb.cinema.demo.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import veb.cinema.demo.models.*;
import veb.cinema.demo.dto.FilmDto;
import veb.cinema.demo.repositories.FilmRepository;
import veb.cinema.demo.repositories.ReviewsRepository;
import veb.cinema.demo.repositories.TicketRepository;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.services.FilmService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
@EnableCaching
public class FilmServiceImpl implements FilmService {

    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private final ReviewsRepository reviewsRepository;

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public FilmServiceImpl(FilmRepository filmRepository, ModelMapper modelMapper, ReviewsRepository reviewsRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
        this.reviewsRepository = reviewsRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(cacheNames = "film", allEntries = true)
    public int create(String name, String genre, String producer, int yearOdPublish, double averageRating) {
        Films film = new Films(name, genre, producer, yearOdPublish, averageRating);
        filmRepository.create(film);
        return film.getId();
    }

    @Override
    public FilmDto findById(int id) {
        Optional<Films> film = filmRepository.findById(id);
        System.out.println("Fetching film from DB: " + id);
        return modelMapper.map(film.orElse(null), FilmDto.class);
    }

    @Override
    public String getFilmNameById(int id) {
        Optional<Films> film = filmRepository.findById(id);
        return film.map(Films::getName).orElse(null);
    }

    @Override
    @Cacheable(value = "film", key = "'allFilms'")
    public List<FilmDto> findAll() {
        return filmRepository.findAll().stream()
                .map(film -> modelMapper.map(film, FilmDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Films> findAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "film", allEntries = true)
    public void update(int id, String name, String genre, String producer, Integer yearOfPublish) {
        Optional<Films> optionalFilms = filmRepository.findById(id);
        if (optionalFilms.isPresent()) {
            Films films = optionalFilms.get();
            films.setName(name);
            films.setProducer(producer);
            films.setYearOfPublish(yearOfPublish);
            films.setGenre(genre);
            filmRepository.update(films);
        } else {
            throw new RuntimeException("Film not found with id: " + id);
        }
    }

    @Override
    public List<FilmDto> getTop3FilmsByAverageRating() {
        List<Films> films = filmRepository.findAll();

        films.forEach(this::setAverageRating);

        films.sort(Comparator.comparingDouble(Films::getAverageRating).reversed());

        return films.stream()
                .limit(5)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void setAverageRating(Films film) {
        double averageRating = calculateAverageRating(film);
        film.setAverageRating(averageRating);
    }

    private double calculateAverageRating(Films film) {
        List<Reviews> reviews = reviewsRepository.findByFilm(film);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double totalRating = reviews.stream().mapToInt(Reviews::getRating).sum();
        return totalRating / reviews.size();
    }

    private FilmDto convertToDTO(Films film) {
        return new FilmDto(film.getId(), film.getName(), film.getAverageRating());
    }
}


