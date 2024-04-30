package com.example.demo.Repositories;

import com.example.demo.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByGenre(String genre);
}
