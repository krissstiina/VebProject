package veb.cinema.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "film")
public class Films extends BaseEntity {
    private String name;
    private String genre;
    private String producer;
    private int yearOfPublish;
    private double averageRating;

    public Films(){}

    public Films(String name, String genre, String producer, int yearOfPublish,double averageRating){
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.yearOfPublish = yearOfPublish;
        this.averageRating = averageRating;
    }

    @Column
    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Column(name = "producer")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Column(name = "year_of_publish")
    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

}