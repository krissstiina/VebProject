package veb.cinema.demo.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import veb.cinema.demo.models.MySession;
import veb.cinema.demo.models.Ticket;
import veb.cinema.demo.repositories.TicketRepository;

@Repository
public class TicketRepositoryImpl extends BaseRepositoryImpl<Ticket> implements TicketRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    public TicketRepositoryImpl() {
        super(Ticket.class);
    }

    @Override
    public List<Ticket> findAllByUserId(int userId) {
        return entityManager.createQuery("SELECT t FROM Ticket t JOIN t.user u WHERE u.id = :userId", Ticket.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Ticket> findByUser(int userId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.user.id = :userId", Ticket.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public long countBySession(MySession session) {
        return entityManager.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.session = :session", Long.class)
                .setParameter("session", session)
                .getSingleResult();
    }

}
