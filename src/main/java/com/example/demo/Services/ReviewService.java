package com.example.demo.Services;

import com.example.demo.Models.Review;
import com.example.demo.Repositories.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public Optional<Review> getById(Long id) {
        return reviewRepo.findById(id);
    }

    public Set<Review> getReviewsByMovie(Long movieId) {
        return reviewRepo.findReviewByMovieId(movieId);
    }

    public void save(Review review) {
        reviewRepo.save(review);
    }
}
