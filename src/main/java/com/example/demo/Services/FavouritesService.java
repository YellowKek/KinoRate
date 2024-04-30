package com.example.demo.Services;

import com.example.demo.Models.Favourites;
import com.example.demo.Models.Movie;
import com.example.demo.Repositories.FavouritesRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavouritesService {
    private final FavouritesRepo favouritesRepo;
    private final MovieService movieService;

    public Set<Movie> getMoviesByUserId(Long id) {
        var optionalFavourites = favouritesRepo.findByUserId(id);
        if (optionalFavourites.isPresent()) {
            return optionalFavourites.get().getMovies();
        }
        return new HashSet<>();
    }

    public Optional<Favourites> getByUserId(Long id) {
        return favouritesRepo.findByUserId(id);
    }

    public Optional<Favourites> getById(Long id) {
        return favouritesRepo.findById(id);
    }

    public void save(Favourites favourites) {
        if (favouritesRepo.findByUser(favourites.getUser()).isEmpty()) {
            favouritesRepo.save(favourites);
        }
    }

    @Transactional
    public void deleteMovieById(Long movieId, Long userId) {
        var optMovie = movieService.findById(movieId);
        var optFav = getByUserId(userId);
        if (optMovie.isPresent() && optFav.isPresent()) {
            var favourites = optFav.get();
            favouritesRepo.deleteMovie(favourites.getId(), movieId);
        }
    }


    @Transactional
    public void addMovie(Long id, Long movieId) {
        var optMovie = movieService.findById(movieId);
        var optFav = getById(id);
        if (optMovie.isPresent() && optFav.isPresent()) {
            var favourites = optFav.get();
            var movie = optMovie.get();
            var moviesInFav = favourites.getMovies();
            if (!moviesInFav.contains(movie)) {
                favouritesRepo.add(favourites.getId(), movieId);
            }
        }
    }
}
