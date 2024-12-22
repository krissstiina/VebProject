package veb.cinema.demo.controllers.admin;

import controllers.admin.AdminSessionController;
import form.mySession.CreateSessionForm;
import form.mySession.SessionSearchForm;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veb.cinema.demo.dto.FilmDto;
import veb.cinema.demo.dto.HallDto;
import veb.cinema.demo.dto.SessionDto;
import veb.cinema.demo.services.FilmService;
import veb.cinema.demo.services.HallService;
import veb.cinema.demo.services.SessionService;
import viewModel.BaseViewModel;
import viewModel.hall.HallCreateViewModel;
import viewModel.mySession.SessionCreateViewModel;
import viewModel.mySession.SessionDetailsViewModel;
import viewModel.mySession.SessionListViewModel;
import viewModel.mySession.SessionViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/mySession")
public class AdminSessionControllerImpl implements AdminSessionController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private SessionService sessionService;
    private FilmService filmService;
    private HallService hallService;

    public AdminSessionControllerImpl(SessionService sessionService,FilmService filmService,HallService hallService) {
        this.sessionService = sessionService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new HallCreateViewModel(
                createBaseViewModel("Создание зала"));
        List<FilmDto> films = filmService.findAll();
        List<HallDto> halls = hallService.findAll();

        model.addAttribute("model", viewModel);
        model.addAttribute("form", new CreateSessionForm("", 0, 0,0,0, ""));
        model.addAttribute("films", films);
        model.addAttribute("halls", halls);
        return "session/adminCreate";
    }


    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CreateSessionForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            List<FilmDto> films = filmService.findAll();
            List<HallDto> halls = hallService.findAll();

            var viewModel = new SessionCreateViewModel(createBaseViewModel("Создание сеанса"));

            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            model.addAttribute("films", films);
            model.addAttribute("halls", halls);
            LOG.log(Level.INFO,"Session is create by admin");

            return "session/adminCreate";
        }

        var id = sessionService.create(form.date(), form.filmId(), form.hallId(),form.price(),form.availableSeats(),form.time());
        return "redirect:/mySession/list";
    }


    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}

