package veb.cinema.demo.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import veb.cinema.demo.models.Films;
import veb.cinema.demo.models.Hall;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


public class SessionDto implements Serializable {
    private int id;
    private String date;
    private int filmId;
    private int hallId;
    private int price;
    private int availableSeats;
    private boolean userHasTicket;
    private String time;

    protected SessionDto(boolean userHasTicket){
        this.userHasTicket = userHasTicket;
    }

    public SessionDto(int id, String date, int filmId, int hallId, int price, int availableSeats, String time){
        this.id = id;
        this.date = date;
        this.filmId = filmId;
        this.hallId = hallId;
        this.price = price;
        this.availableSeats = availableSeats;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SessionDto() {
    }

    public boolean isUserHasTicket() {
        return userHasTicket;
    }

    public void setUserHasTicket(boolean userHasTicket) {
        this.userHasTicket = userHasTicket;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
