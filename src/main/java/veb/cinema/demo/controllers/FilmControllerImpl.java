package veb.cinema.demo.controllers;

import controllers.film.FilmController;
import form.films.FilmSearchForm;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import veb.cinema.demo.dto.FilmDto;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.services.FilmService;
import veb.cinema.demo.services.UserService;
import viewModel.BaseViewModel;
import viewModel.film.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/films")
public class FilmControllerImpl implements FilmController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private FilmService filmService;
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public FilmControllerImpl(FilmService filmService, UserService userService, UserRepository userRepository) {
        this.filmService = filmService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    @GetMapping()
    public String listFilms(@ModelAttribute("form") FilmSearchForm form, Model model) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";

        List<FilmDto> allFilms = filmService.findAll();

        System.out.println("All films: " + allFilms);

        List<FilmDto> filteredFilms = allFilms.stream()
                .filter(f -> f != null && f.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());

        var filmsViewModels = filteredFilms.stream()
                .filter(f -> f != null)
                .map(f -> new FilmViewModel(f.getId(), f.getName(), f.getGenre(), f.getProducer(), f.getYearOfPublish()))
                .toList();

        var viewModel = new FilmListViewModel(
                createBaseViewModel("Список фильмов"),
                filmsViewModels,
                filteredFilms.size()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        LOG.log(Level.INFO,"All films in the cinema");

        return "film/list";
    }

    @Override
    @GetMapping("/topByRating")
    public String getTopFilms(Model model) {
        List<FilmDto> topFilms = filmService.getTop3FilmsByAverageRating();

        List<CustomFilmViewModel> filmViewModels = topFilms.stream()
                .map(f -> new CustomFilmViewModel(f.getName(), f.getProducer(), f.getAverageRating()))
                .collect(Collectors.toList());

        var viewModel = new TopFilmsViewModel(
                createBaseViewModel("Топ 5 фильмов по среднему рейтингу от пользователей"),
                filmViewModels
        );
        LOG.log(Level.INFO,"Open page with top by average rating");
        model.addAttribute("model", viewModel);
        return "topByRating";
    }

    @Override
    @GetMapping("/{id}")
    public String details(int id, Model model) {
        var film = filmService.findById(id);
        var viewModel = new FilmDetailsViewModel(
                createBaseViewModel("Детали фильма"),
                new FilmViewModel(film.getId(),
                        film.getName(),
                        film.getGenre(),
                        film.getProducer(),
                        film.getYearOfPublish())
        );
        LOG.log(Level.INFO,"Open page with film details");
        model.addAttribute("model", viewModel);
        return "film/details";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
