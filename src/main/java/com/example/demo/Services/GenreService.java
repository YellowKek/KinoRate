package com.example.demo.Services;

import com.example.demo.Models.Genre;
import com.example.demo.Repositories.GenreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepo genreRepo;

    public Optional<Genre> getById(Long id) {
        return genreRepo.findById(id);
    }

    public Optional<Genre> getByGenre(String genre) {
        return genreRepo.findGenreByGenre(genre);
    }

    public List<Genre> getAll() {
        return genreRepo.findAll();
    }
}
