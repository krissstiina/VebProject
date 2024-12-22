package veb.cinema.demo.dto;

import veb.cinema.demo.models.Hall;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class SessionCreateDto {
    private String date;
    private int filmId;
    private int hallId;
    private int price;
    private String time;

    protected SessionCreateDto(String time){
        this.time = time;
    }

    public SessionCreateDto(String date, int filmId, int hallId, int price, String time){
        this.date = date;
        this.filmId = filmId;
        this.hallId = hallId;
        this.price = price;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
