package veb.cinema.demo.dto;

public class ReviewsCreateDto {
    private int userId;
    private String review;
    private int filmId;
    private int rating;

    protected ReviewsCreateDto() {}

    public ReviewsCreateDto(int userId, String review, int filmId,int rating) {
        this.filmId = filmId;
        this.userId = userId;
        this.review = review;
        this. rating = rating;
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
