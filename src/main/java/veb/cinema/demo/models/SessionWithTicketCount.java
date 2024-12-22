package veb.cinema.demo.models;

import java.time.LocalDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SessionWithTicketCount {
    private MySession mySession;
    private long ticketCount;

    // Constructor
    public SessionWithTicketCount(MySession mySession, long ticketCount) {
        this.mySession = mySession;
        this.ticketCount = ticketCount;
    }

    // Getter for MySession
    public MySession getMySession() {
        return mySession;
    }

    // Getter for Ticket Count
    public long getTicketCount() {
        return ticketCount;
    }

    // Get formatted film name
    public String getFilmName() {
        return mySession.getFilm().getName();  // Assumes session has a Film object with getName method
    }

    // Get formatted date as string
    public String getFormattedDate() {
        LocalDate date = mySession.getDate();  // Assuming session.getDate() returns LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // You can adjust this pattern
        return date.format(dateFormatter);  // Format the date into a string
    }

    // Get formatted time as string
    public String getFormattedTime() {
        LocalTime time = mySession.getTime();  // Assuming session.getTime() returns LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");  // You can adjust this pattern
        return time.format(timeFormatter);  // Format the time into a string
    }

    // Get hall number
    public int getHallNumber() {
        return mySession.getHall().getHallNumber();  // Assumes session has a Hall object with getHallNumber method
    }
}
