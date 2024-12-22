package veb.cinema.demo.services;

import java.util.List;

import veb.cinema.demo.dto.SessionDto;
import veb.cinema.demo.dto.SessionWithTicketCountDto;
import veb.cinema.demo.dto.TicketDto;
import veb.cinema.demo.models.Ticket;

public interface TicketService {
    TicketDto findById(int id);
    List<TicketDto> findAll();
    int create(int sessionId, int userId);
    List<Ticket> findByCurrentUser();
    SessionWithTicketCountDto countBySession(SessionDto session);

}
