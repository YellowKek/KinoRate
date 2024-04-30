package com.example.demo.Repositories;

import com.example.demo.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    Set<Review> findReviewByMovieId(Long movieId);
}
