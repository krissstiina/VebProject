package veb.cinema.demo.dto;

public class SessionCountDto {
    private int sessionId;
    private String date;
    private int filmId;
    private int hallId;
    private int price;
    private int availableSeats;
    private String time;
    private long ticketCount;


    public SessionCountDto(int sessionId, String date, int filmId, int hallId, int price, int availableSeats, String time, long ticketCount) {
        this.sessionId = sessionId;
        this.date = date;
        this.filmId = filmId;
        this.hallId = hallId;
        this.price = price;
        this.availableSeats = availableSeats;
        this.time = time;
        this.ticketCount = ticketCount;
    }
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
