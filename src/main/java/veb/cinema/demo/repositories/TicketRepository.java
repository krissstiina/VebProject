package veb.cinema.demo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import veb.cinema.demo.models.MySession;
import veb.cinema.demo.models.Ticket;
import veb.cinema.demo.repositories.generic.CreateRepository;
import veb.cinema.demo.repositories.generic.DeleteRepository;
import veb.cinema.demo.repositories.generic.ReadRepository;
import veb.cinema.demo.repositories.generic.UpdateRepository;

@Repository
public interface TicketRepository extends ReadRepository<Ticket>, CreateRepository<Ticket>, UpdateRepository<Ticket>, DeleteRepository<Ticket> {
    List<Ticket> findAllByUserId(int userId);
    long countBySession(MySession session);
    List<Ticket> findByUser(int userId);
}
