package veb.cinema.demo.controllers;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import controllers.reviews.ReviewsController;
import form.reviews.CreateReviewsForm;
import form.reviews.EditReviewsForm;
import form.reviews.ReviewsSearchForm;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import veb.cinema.demo.dto.FilmDto;
import veb.cinema.demo.dto.ReviewsDto;
import veb.cinema.demo.dto.UserDto;
import veb.cinema.demo.dto.UserProfileDto;
import veb.cinema.demo.models.Reviews;
import veb.cinema.demo.models.User;
import veb.cinema.demo.services.AuthService;
import veb.cinema.demo.services.FilmService;
import veb.cinema.demo.services.ReviewsService;
import veb.cinema.demo.services.UserService;
import viewModel.BaseViewModel;
import viewModel.reviews.ReviewsCreateViewModel;
import viewModel.reviews.ReviewsEditViewModel;
import viewModel.reviews.ReviewsListViewModel;
import viewModel.reviews.ReviewsViewModel;

@Controller
@RequestMapping("/reviews")
public class ReviewsControllerImpl implements ReviewsController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private ReviewsService reviewsService;

    private FilmService filmService;

    private UserService userService;

    private final AuthService authService;

    public ReviewsControllerImpl(ReviewsService reviewsService,FilmService filmService,UserService userService, AuthService authService) {
        this.reviewsService = reviewsService;
        this.userService = userService;
        this.filmService = filmService;
        this.authService = authService;
    }
    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new ReviewsCreateViewModel(createBaseViewModel("Создание отзыва"));
        List<FilmDto> films = filmService.findAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserProfileDto userProfileDto = userService.getUserProfile(currentUsername);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", new CreateReviewsForm(0, "", 0, 0));
        model.addAttribute("films", films);
        model.addAttribute("user", userProfileDto);
        return "review/create";
    }


    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") CreateReviewsForm form, BindingResult bindingResult, Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        UserProfileDto userProfileDtoOptional = userService.getUserProfile(currentUsername);


        UserProfileDto userProfileDto = userProfileDtoOptional;
        int userId = userProfileDto.getId();

        if (bindingResult.hasErrors()) {
            List<FilmDto> films = filmService.findAll();
            model.addAttribute("model", new ReviewsCreateViewModel(createBaseViewModel("Создание отзыва")));
            model.addAttribute("form", form);
            model.addAttribute("films", films);
            model.addAttribute("user", userProfileDto);
            LOG.log(Level.INFO,"Review create by " + principal.getName());
            return "review/create";
        }

        try {
            int reviewId = reviewsService.create(userId, form.review(), form.filmId(), form.rating());
            return "redirect:/reviews";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "review/create";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "review/create";
        }
    }


    @Override
    @GetMapping
    public String listReviews(@ModelAttribute("form") ReviewsSearchForm form, Model model) {
        String searchTerm = form.searchTerm() != null ? form.searchTerm().toLowerCase() : "";

        List<FilmDto> films = filmService.findAll();
        List<UserDto> users = userService.findAll();

        Map<Integer, String> filmIdToNameMap = films.stream()
                .collect(Collectors.toMap(FilmDto::getId, FilmDto::getName));

        Map<Integer, String> userIdToNameMap = users.stream()
                .collect(Collectors.toMap(UserDto::getId, UserDto::getName));

        List<ReviewsDto> filteredReviews = reviewsService.findAll().stream()
                .filter(review -> filmIdToNameMap.containsKey(review.getFilmId()) &&
                        filmIdToNameMap.get(review.getFilmId()).toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        List<ReviewsViewModel> reviewsViewModels = filteredReviews.stream()
                .map(r -> new ReviewsViewModel(
                        r.getId(),
                        userIdToNameMap.get(r.getUserId()),
                        r.getReview(),
                        filmIdToNameMap.get(r.getFilmId()),
                        r.getRating()))
                .toList();

        var viewModel = new ReviewsListViewModel(
                createBaseViewModel("Список отзывов"),
                reviewsViewModels,
                filteredReviews.size()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", new ReviewsSearchForm(searchTerm, 1, filteredReviews.size()));
        LOG.log(Level.INFO,"All reviews " );


        return "review/list";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model, Principal principal) {
        var review = reviewsService.findById(id);

        var viewModel = new ReviewsEditViewModel(createBaseViewModel("Редактирование отзыва"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EditReviewsForm(review.getId(), review.getUserId(), review.getReview(), review.getRating(), review.getFilmId()));
        return "review/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") EditReviewsForm form, BindingResult bindingResult, Model model, Principal principal) {
        var review = reviewsService.findById(id);

        if (bindingResult.hasErrors()) {
            var viewModel = new ReviewsEditViewModel(createBaseViewModel("Редактирование отзыва"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "review/edit";
        }

        reviewsService.update(id, form.userId(), form.review(), form.filmId(), form.rating());

        return "redirect:/reviews";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
