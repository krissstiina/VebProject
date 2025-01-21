package veb.cinema.demo.services;

import java.util.List;

import veb.cinema.demo.dto.ReviewsDto;
import veb.cinema.demo.models.Reviews;

public interface ReviewsService {
    ReviewsDto findById(int id);
    int create(int userId, String review, int filmId, int rating);
    List<ReviewsDto> findAll();
    void deleteReview(int id);
    boolean isAuthor(int reviewId, String username);
    Reviews getReviewById(int id);
    Reviews save(Reviews review);
    void update(int id, int userId, String review, int filmId, int rating);
    List<ReviewsDto> findAllByFilmId(Integer filmId);
}
