package veb.cinema.demo.controllers.admin;

import controllers.admin.AdminHallController;
import form.hall.CreateHallForm;
import form.hall.HallSearchForm;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veb.cinema.demo.dto.HallDto;
import veb.cinema.demo.services.HallService;
import viewModel.BaseViewModel;
import viewModel.hall.HallCreateViewModel;
import viewModel.hall.HallDetailsViewModel;
import viewModel.hall.HallListViewModel;
import viewModel.hall.HallViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/halls")
public class AdminHallControllerImpl implements AdminHallController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private HallService hallService;

    @Autowired
    public AdminHallControllerImpl(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new HallCreateViewModel(
                createBaseViewModel("Создание зала"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new CreateHallForm(0, 0));
        return "hall/adminCreate";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CreateHallForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new HallCreateViewModel(
                    createBaseViewModel("Создание зала")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "hall/adminCreate";
        }

        var id = hallService.create(form.capacity(),form.hallNumber());
        LOG.log(Level.INFO,"Hall is create by admin");
        return "redirect:/halls/" + id;
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

        return "hall/adminList";
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
        return "hall/adminDetails";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
