package com.example.demo.Repositories;

import com.example.demo.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);

    @Query(value = "select movie_id from movie_genre where genre_id = :genreId", nativeQuery = true)
    Set<Long> findByGenre(Long genreId);
}
