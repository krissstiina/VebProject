package veb.cinema.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "session")
public class MySession extends BaseEntity {
    private LocalDate date;
    private Films film;
    private Hall hall;
    private int price;
    private int availableSeats;


    private LocalTime time;

    public MySession(LocalTime time){
        this.time = time;
    }

    public MySession(LocalDate date, Films film, Hall hall, int price, int availableSeats, LocalTime time){
        this.date = date;
        this.film = film;
        this.hall = hall;
        this.price = price;
        this.availableSeats = availableSeats;
        this.time = time;
    }

    public int getPriceForUser(int age) {
        if (age < 18) {
            return price - 50;
        }
        return price;
    }

    public MySession() {

    }

    @Column(name = "session_time")
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Column
    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "hall_id")
    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    public Films getFilm() {
        return film;
    }

    public void setFilm(Films film) {
        this.film = film;
    }
}


