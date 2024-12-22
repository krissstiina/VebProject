package veb.cinema.demo.controllers.admin;

import controllers.admin.AdminTicketController;

import form.ticket.CreateTicketForm;
import form.ticket.EditTicketForm;
import form.ticket.TicketSearchForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import veb.cinema.demo.dto.TicketDto;
import veb.cinema.demo.services.TicketService;
import viewModel.BaseViewModel;
import viewModel.ticket.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/ticket")
public class AdminTicketControllerImpl implements AdminTicketController {

    private TicketService ticketService;

    public AdminTicketControllerImpl(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new TicketCreateViewModel(
                createBaseViewModel("Создание билета"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new CreateTicketForm(0,0));
        return "ticket-create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CreateTicketForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new TicketCreateViewModel(
                    createBaseViewModel("Создание билета")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "ticket-create";
        }

        var id = ticketService.create(form.userId(), form.sessionId());
        return "redirect:/ticket/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
