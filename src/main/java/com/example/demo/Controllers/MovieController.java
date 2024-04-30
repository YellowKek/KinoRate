package com.example.demo.Controllers;

import com.example.demo.Models.Movie;
import com.example.demo.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;


@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final FavouritesService favouritesService;
    private final UserService userService;
    private final GenreService genreService;

    @GetMapping("/")
    public String getAllMovies(@RequestParam(name = "genre", required = false, defaultValue = "all") String genre, Model model) {
        if (genre.equals("all")) {
            model.addAttribute("movies", movieService.findAll());
        } else {
            model.addAttribute("movies", movieService.findByGenre(genre));
        }
        model.addAttribute("genres", genreService.getAll());
        return "index";
    }

    @GetMapping("/{movie_id}")
    public String getMovie(@PathVariable("movie_id") Long movieId, Model model, @AuthenticationPrincipal User authUser) {
        boolean inFavourites = false;
        if (movieService.findById(movieId).isPresent()) {
            model.addAttribute("movie", movieService.findById(movieId).get());
            if (authUser != null) {
                var user = userService.findByUsername(authUser.getUsername()).get();
                var movies = favouritesService.getMoviesByUserId(user.getId());
                for (Movie movie : movies) {
                    if (Objects.equals(movie.getId(), movieId)) {
                        inFavourites = true;
                        break;
                    }
                }
            }
        }
        model.addAttribute("in_fav", inFavourites);
        return "about_film";
    }

    @GetMapping("thrillers")
    public String getThrillers(Model model) {
        model.addAttribute("thrillers", movieService.findAll());
        return "thrillers";
    }

}
