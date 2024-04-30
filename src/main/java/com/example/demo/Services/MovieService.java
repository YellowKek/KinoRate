package com.example.demo.Services;

import com.example.demo.Models.Movie;
import com.example.demo.Repositories.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepo movieRepo;
    private final GenreService genreService;

    public Optional<Movie> getById(Long id) {
        return movieRepo.findById(id);
    }

    public List<Movie> findAll() {
        return movieRepo.findAll();
    }

    public Optional<Movie> findById(long id) {
        return movieRepo.findById(id);
    }

    public Optional<Movie> findByName(String name) {
        return movieRepo.findByName(name);
    }

    public Set<Movie> findByGenre(String genre) {
        var genreId = genreService.getByGenre(genre).get().getId();
        var moviesId = movieRepo.findByGenre(genreId);
        Set<Movie> res = new HashSet<>();
        for (Long id : moviesId) {
            var temp = findById(id);
            temp.ifPresent(res::add);
        }
        return res;
    }
}
