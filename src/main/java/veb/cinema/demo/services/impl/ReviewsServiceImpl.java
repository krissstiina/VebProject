package veb.cinema.demo.services.impl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import veb.cinema.demo.dto.UserDto;
import veb.cinema.demo.models.Films;
import veb.cinema.demo.models.User;
import veb.cinema.demo.models.Reviews;
import veb.cinema.demo.dto.ReviewsDto;
import veb.cinema.demo.repositories.FilmRepository;
import veb.cinema.demo.repositories.ReviewsRepository;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.services.ReviewsService;
import veb.cinema.demo.services.UserService;

@Service
@EnableCaching
public class ReviewsServiceImpl implements ReviewsService {
    private static final String CACHE_KEY = "review";

    @Autowired
    private final ReviewsRepository reviewsRepository;

    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ModelMapper modelMapper;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ModelMapper modelMapper, FilmRepository filmRepository, UserRepository userRepository, UserService userService) {
        this.reviewsRepository = reviewsRepository;
        this.modelMapper = modelMapper;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public int create(int userId, String review, int filmId, int rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Films films = filmRepository.findById(filmId)
                .orElseThrow(() -> new IllegalArgumentException("Film not found"));

        Reviews reviews = new Reviews(user, review, films, rating);

        Reviews createdReview = reviewsRepository.save(reviews);

        return createdReview.getId();
    }

    @Override
    public ReviewsDto findById(int id) {
        Optional<Reviews> reviews = reviewsRepository.findById(id);
        return reviews.map(review -> modelMapper.map(review, ReviewsDto.class)).orElse(null);
    }

    @Override
    public Reviews getReviewById(int id) {
        return reviewsRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isAuthor(int reviewId, String username) {
        Optional<Reviews> reviewOptional = reviewsRepository.findById(reviewId);
        return reviewOptional.map(review -> review.getUserId().getName().equals(username)).orElse(false);
    }

    @Override
    @CachePut(value = CACHE_KEY, key = "#result.id")
    public Reviews save(Reviews review) {
        return reviewsRepository.save(review);
    }

    @Override
    @CacheEvict(value = CACHE_KEY)
    @CachePut(value = CACHE_KEY)
    public void update(int id, int userId, String review, int filmId, int rating) {
        Optional<Reviews> optionalReview = reviewsRepository.findById(id);
        if (optionalReview.isPresent()) {
            Reviews reviews = optionalReview.get();
            reviews.setReview(review);
            Films film = new Films();
            film.setId(filmId);
            reviews.setFilm(film);
            User user = new User();
            user.setId(userId);
            reviews.setUserId(user);
            reviews.setRating(rating);
            reviewsRepository.update(reviews);
        } else {
            throw new RuntimeException("Review not found with id: " + id);
        }
    }

    @Override
    public void deleteReview(int id) {
        reviewsRepository.deleteById(id);
    }

    @Override
    public List<ReviewsDto> findAllByFilmId(Integer filmId) {
        List<Reviews> reviews = reviewsRepository.findAllByFilmId(filmId);

        return reviews.stream()
                .map(review -> new ReviewsDto(review))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = CACHE_KEY)
    public List<ReviewsDto> findAll() {
        return reviewsRepository.findAll().stream()
                .map((reviews) -> modelMapper.map(reviews, ReviewsDto.class))
                .toList();
    }
}
