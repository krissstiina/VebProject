package veb.cinema.demo.controllers;

import controllers.session.SessionController;
import form.mySession.SessionSearchForm;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import veb.cinema.demo.dto.FilmDto;
import veb.cinema.demo.dto.HallDto;
import veb.cinema.demo.dto.SessionDto;
import veb.cinema.demo.exception.UserNotFoundException;
import veb.cinema.demo.models.Films;
import veb.cinema.demo.services.FilmService;
import veb.cinema.demo.services.HallService;
import veb.cinema.demo.services.SessionService;
import veb.cinema.demo.services.UserService;
import viewModel.BaseViewModel;
import viewModel.mySession.SessionDetailsViewModel;
import viewModel.mySession.SessionListViewModel;
import viewModel.mySession.SessionViewModel;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mySession")
public class SessionControllerImpl implements SessionController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private SessionService sessionService;
    private FilmService filmService;
    private HallService hallService;
    private final ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public SessionControllerImpl(SessionService sessionService, FilmService filmService, HallService hallService,
                                 ModelMapper modelMapper, UserService userService) {
        this.sessionService = sessionService;
        this.filmService = filmService;
        this.hallService = hallService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    @GetMapping("/list")
    public String listSession(@ModelAttribute("form") SessionSearchForm form, Model model) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm().toLowerCase() : "";


        List<SessionDto> allSession = sessionService.findAll();
        List<Films> film = filmService.findAllFilms();

        Map<Integer, String> filmsMap = film.stream()
                .collect(Collectors.toMap(Films::getId, Films::getName));

        List<SessionDto> filteredSession = allSession.stream()
                .filter(session -> {
                    String filmName = filmsMap.get(session.getFilmId());
                    return filmName != null && filmName.contains(searchTerm)
                            || session.getDate().contains(searchTerm);
                })
                .collect(Collectors.toList());

        Map<Integer, String> films = filmService.findAll().stream()
                .collect(Collectors.toMap(FilmDto::getId, FilmDto::getName));

        Map<Integer, Integer> halls = hallService.findAll().stream()
                .collect(Collectors.toMap(HallDto::getId, HallDto::getHallNumber));

        var sessionViewModels = filteredSession.stream()
                .map(s -> new SessionViewModel(
                        s.getId(),
                        s.getDate(),
                        s.getFilmId(),
                        s.getHallId(),
                        s.getPrice(),
                        s.getAvailableSeats(),
                        s.getTime()
                ))
                .collect(Collectors.toList());

        var viewModel = new SessionListViewModel(
                createBaseViewModel("Список сеансов"),
                sessionViewModels,
                1
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", new SessionSearchForm(searchTerm, 1, filteredSession.size()));
        model.addAttribute("films", films);
        model.addAttribute("halls", halls);
        LOG.log(Level.INFO,"All sessions " );

        return "session/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable("id") int id, Model model) {
        var session = sessionService.findById(id);

        if (session == null) {
            return "redirect:/error";
        }

        var viewModel = new SessionDetailsViewModel(
                createBaseViewModel("Детали сеанса"),
                new SessionViewModel(session.getId(),
                        session.getDate(),
                        session.getFilmId(),
                        session.getHallId(),
                        session.getPrice(),
                        session.getAvailableSeats(),
                        session.getTime())
        );

        String filmName = filmService.getFilmNameById(session.getFilmId());
        int numberHall = hallService.getHallNumberById(session.getHallId());

        model.addAttribute("model", viewModel);
        model.addAttribute("filmName", filmName);
        model.addAttribute("numberHall", numberHall);
        LOG.log(Level.INFO,"Page with details of session " );

        return "session/details";
    }


    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

    @Override
    @GetMapping("/{sessionId}/book")
    public String showBookingForm(@PathVariable("sessionId") Integer sessionId, Model model) {
        SessionDto mySession = sessionService.findById(sessionId);
        if (mySession == null) {
            model.addAttribute("errorMessage", "Сессия не найдена.");
            return "error";
        }

        var viewModel = new BaseViewModel("Бронирование места");
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("model", viewModel);
        model.addAttribute("mySession", mySession);
        return "booking";
    }

    @Override
    @PostMapping("/{sessionId}/book")
    public String bookSeat(@PathVariable("sessionId") Integer sessionId, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            SessionDto sessionDto = sessionService.bookSeat(sessionId);

            redirectAttributes.addFlashAttribute("message", "Место успешно забронировано!");
            LOG.log(Level.INFO,"Session is book by user  " + principal.getName() );
            return "redirect:/mySession/" + sessionId;
        } catch (IllegalStateException | IllegalArgumentException | UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mySession/" + sessionId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Произошла непредвиденная ошибка: " + e.getMessage());
            return "redirect:/mySession/" + sessionId;
        }
    }
}
