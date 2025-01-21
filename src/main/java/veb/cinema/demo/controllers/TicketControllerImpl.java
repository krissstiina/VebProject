package veb.cinema.demo.controllers;

import controllers.ticket.TicketController;
import form.ticket.TicketSearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import veb.cinema.demo.dto.SessionDto;
import veb.cinema.demo.dto.SessionWithTicketCountDto;
import veb.cinema.demo.dto.TicketDto;
import veb.cinema.demo.models.MySession;
import veb.cinema.demo.models.SessionWithTicketCount;
import veb.cinema.demo.models.Ticket;
import veb.cinema.demo.repositories.TicketRepository;
import veb.cinema.demo.services.SessionService;
import veb.cinema.demo.services.TicketService;
import viewModel.BaseViewModel;
import viewModel.reviews.ReviewsCreateViewModel;
import viewModel.ticket.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ticket")
public class TicketControllerImpl implements TicketController {

    private TicketService ticketService;

    private SessionService sessionService;


    public TicketControllerImpl(TicketService ticketService,SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @Override
    @GetMapping("/user/{userId}")
    public String showUserTickets(Model model) {
        List<Ticket> tickets = ticketService.findByCurrentUser();
        model.addAttribute("tickets", tickets);
        return "ticket/userTickets";
    }

    @Override
    @PostMapping("/cancel/{ticketId}")
    public String cancelBooking(@PathVariable("ticketId") Integer ticketId, RedirectAttributes redirectAttributes) {
        try {
            sessionService.cancelBooking(ticketId);
            redirectAttributes.addFlashAttribute("message", "Бронирование успешно отменено!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Произошла непредвиденная ошибка: " + e.getMessage());
        }
        return "redirect:/mySession/list";
    }



    private TicketViewModel convertToTicketViewModel(TicketDto ticketDto) {
        return new TicketViewModel(ticketDto.getId(), ticketDto.getSessionId(), ticketDto.getUserId());
    }

    @GetMapping("/top")
    public String getTop3MostPurchasedSessions(Model model) {
        List<SessionDto> topSessions = sessionService.getTop3MostPurchasedSessions();

        model.addAttribute("topSessions", topSessions);

        return "top";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
