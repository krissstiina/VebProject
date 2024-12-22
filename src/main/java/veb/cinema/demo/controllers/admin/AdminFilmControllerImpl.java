package veb.cinema.demo.controllers.admin;

import controllers.admin.AdminFilmController;
import form.films.CreateFilmForm;
import form.films.EditFilmForm;
import form.films.FilmSearchForm;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import veb.cinema.demo.dto.FilmDto;
import veb.cinema.demo.services.FilmService;
import viewModel.BaseViewModel;
import viewModel.film.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/films")
public class AdminFilmControllerImpl implements AdminFilmController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private FilmService filmService;

    @Autowired
    public AdminFilmControllerImpl(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new FilmCreateViewModel(createBaseViewModel("Создание фильма"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new CreateFilmForm(null, null, null,null,0));
        return "film/AdminCreate";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CreateFilmForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new FilmCreateViewModel(createBaseViewModel("Создание фильма"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "film/AdminCreate";
        }
        var id = filmService.create(
                form.name(),
                form.genre(),
                form.producer(),
                form.yearOfPublish(),
                form.averageRating());
        LOG.log(Level.INFO,"Film is create by admin");
        return "redirect:/films/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(int id, Model model) {
        var film = filmService.findById(id);
        var viewModel = new FilmEditViewModel(
                createBaseViewModel("Редактирование фильма")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EditFilmForm(film.getId(),film.getName(),film.getGenre(),film.getProducer(),film.getYearOfPublish()));
        return "film/adminEdit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") EditFilmForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new FilmEditViewModel(
                    createBaseViewModel("Редактирование фильма")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "film/adminEdit";
        }

        filmService.update(id, form.name(), form.genre(), form.producer(),form.yearOfPublish());
        LOG.log(Level.INFO,"Film is edit by admin");
        return "redirect:/admin/films/" + id;
    }



    @Override
    @GetMapping()
    public String listFilms(@ModelAttribute("form") FilmSearchForm form, Model model) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";

        List<FilmDto> allFilms = filmService.findAll();

        List<FilmDto> filteredFilms = allFilms.stream()
                .filter(f -> f.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());

        List<FilmViewModel> filmsViewModels = filteredFilms.stream()
                .map(f -> new FilmViewModel(f.getId(), f.getName(), f.getGenre(), f.getProducer(), f.getYearOfPublish()))
                .collect(Collectors.toList());
        var viewModel = new FilmListViewModel(
                createBaseViewModel("Список фильмов"),
                filmsViewModels,
                1
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "film/adminList";
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
        model.addAttribute("model", viewModel);
        return "film/details";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
