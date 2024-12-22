package veb.cinema.demo.controllers;

import controllers.hall.HallController;
import form.hall.HallSearchForm;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb.cinema.demo.dto.HallDto;
import veb.cinema.demo.services.HallService;
import viewModel.BaseViewModel;
import viewModel.hall.HallDetailsViewModel;
import viewModel.hall.HallListViewModel;
import viewModel.hall.HallViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/halls")
public class HallControllerImpl implements HallController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private HallService hallService;

    @Autowired
    public HallControllerImpl(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    @GetMapping()
    public String listHalls(@ModelAttribute("form") HallSearchForm form, Model model) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";

        List<HallDto> allHalls = hallService.findAll();

        List<HallDto> filteredHalls = allHalls.stream()
                .filter(h -> String.valueOf(h.getHallNumber()).contains(searchTerm)
                        || String.valueOf(h.getCapacity()).contains(searchTerm))
                .collect(Collectors.toList());

        var hallViewModels = filteredHalls.stream()
                .map(h -> new HallViewModel(h.getId(), h.getHallNumber(), h.getCapacity()))
                .toList();

        var viewModel = new HallListViewModel(
                createBaseViewModel("Список залов"),
                hallViewModels,
                filteredHalls.size()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        LOG.log(Level.INFO,"All halls in the cinema");

        return "hall/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(int id, Model model) {
        var hall = hallService.findById(id);
        var viewModel = new HallDetailsViewModel(
                createBaseViewModel("Детали зала"),
                new HallViewModel(hall.getId(),
                        hall.getCapacity(),
                        hall.getHallNumber())
        );
        model.addAttribute("model", viewModel);
        LOG.log(Level.INFO,"Halls details in the cinema");
        return "hall/details";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}

