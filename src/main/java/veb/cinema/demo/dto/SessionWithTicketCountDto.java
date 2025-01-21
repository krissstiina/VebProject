package veb.cinema.demo.dto;

public class SessionWithTicketCountDto {
    private long sessionId;
    private String filmName;
    private String sessionDate;
    private String sessionTime;
    private int hallNumber;
    private int price;
    private int availableSeats;
    private long ticketCount;

    public SessionWithTicketCountDto(long sessionId, String filmName, String sessionDate,
                                     String sessionTime, int hallNumber, int price,
                                     int availableSeats, long ticketCount) {
        this.sessionId = sessionId;
        this.filmName = filmName;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.hallNumber = hallNumber;
        this.price = price;
        this.availableSeats = availableSeats;
        this.ticketCount = ticketCount;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
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

    public long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
