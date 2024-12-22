package veb.cinema.demo.dto;

import veb.cinema.demo.models.Reviews;

import java.io.Serializable;

public class ReviewsDto implements Serializable {
    private int id;
    private int userId;
    private String review;
    private int filmId;
    private int rating;



    protected ReviewsDto() {}

    public ReviewsDto(int id, int userId, String review, int rating) {
        this.id = id;
        this.userId = userId;
        this.review = review;
        this.rating = rating;
    }

    public ReviewsDto(Reviews review) {
        this.id = review.getId();
        this.review = review.getReview();  // Метод для получения текста отзыва
        this.rating = review.getRating();          // Метод для получения оценки отзыва// Метод для получения имени пользователя
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
