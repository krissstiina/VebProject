package veb.cinema.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Reviews extends BaseEntity {
    private Films film;
    private User userId;
    private String review;
    private int rating;

    public Reviews() {}

    public Reviews( User userId, String review, Films filmId,int rating) {
        this.film = filmId;
        this.userId = userId;
        this.review = review;
        this.rating = rating;
    }

    @Column
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Column(name = "review")
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @ManyToOne
    @JoinColumn(name = "film_id")
    public Films getFilm() {
        return film;
    }

    public void setFilm(Films filmId) {
        this.film = filmId;
    }
}
