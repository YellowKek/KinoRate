package com.example.demo.Controllers;

import com.example.demo.Models.Review;
import com.example.demo.Services.MovieService;
import com.example.demo.Services.ReviewService;
import com.example.demo.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final MovieService movieService;
    private final UserService userService;

    @GetMapping("/getForm/{movie_id}")
    public String leaveReviewPage(@PathVariable("movie_id") Long movieId, Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("movie_id", movieId);
        return "review_form";
    }

    @PostMapping("/{movie_id}")
    public String leaveReview(@AuthenticationPrincipal User authUSer, @PathVariable("movie_id") Long movieId, @ModelAttribute("review") @Valid Review review) {
        var optMovie = movieService.getById(movieId);
        var user = userService.findByUsername(authUSer.getUsername());
        if (optMovie.isPresent()) {
            var movie = optMovie.get();
            review.setMovie(movie);
            review.setUser(user.get());
            reviewService.save(review);
        }

        return "redirect:{movie_id}";
    }

    @GetMapping("/{movie_id}")
    public String reviewsPage(@PathVariable("movie_id") Long movieId, Model model) {
        System.out.println(reviewService.getReviewsByMovie(movieId));
        model.addAttribute("reviews", reviewService.getReviewsByMovie(movieId));
        return "reviews_by_film";
    }
}
