package veb.cinema.demo.dto;

import veb.cinema.demo.models.Films;

import java.io.Serializable;

public class FilmDto implements Serializable {
    private int id;
    private String name;
    private String genre;
    private String producer;
    private int yearOfPublish;
    private double averageRating;

    public FilmDto(int id, String name, String genre, String producer, int yearOfPublish){
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.yearOfPublish = yearOfPublish;
    }

    protected FilmDto() {}

    public FilmDto(int id, String name, double averageRating) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getProducer() {
        return producer;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public void setName(String name) {
        this.name = name;
    }

}

