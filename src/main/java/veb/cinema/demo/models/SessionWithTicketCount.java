package veb.cinema.demo.models;

import java.time.LocalDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SessionWithTicketCount {
    private MySession mySession;
    private long ticketCount;

    public SessionWithTicketCount(MySession mySession, long ticketCount) {
        this.mySession = mySession;
        this.ticketCount = ticketCount;
    }

    public MySession getMySession() {
        return mySession;
    }

    public long getTicketCount() {
        return ticketCount;
    }

    public String getFilmName() {
        return mySession.getFilm().getName();
    }

    public String getFormattedDate() {
        LocalDate date = mySession.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateFormatter);
    }

    public String getFormattedTime() {
        LocalTime time = mySession.getTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(timeFormatter);
    }

    public int getHallNumber() {
        return mySession.getHall().getHallNumber();
    }
}
