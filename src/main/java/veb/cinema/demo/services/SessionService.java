package veb.cinema.demo.services;

import java.util.List;

import veb.cinema.demo.dto.SessionDto;

public interface SessionService {
    SessionDto findById(int id);
    List<SessionDto> findAll();
    int create(String date, int filmId, int hallId, int price, int availableSeats, String time);
    SessionDto bookSeat(int sessionId) throws Exception;
    List<SessionDto> getTop3MostPurchasedSessions();
    void cancelBooking(int ticketId);
}
