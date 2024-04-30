package com.example.demo.Controllers;

import com.example.demo.Services.FavouritesService;
import com.example.demo.Services.MovieService;
import com.example.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/favourites")
@RequiredArgsConstructor
public class FavouritesController {
    private final FavouritesService favouritesService;
    private final UserService userService;
    private final MovieService movieService;

    @GetMapping
    public String getByUserId(@AuthenticationPrincipal User user, Model model) {
        var userToSearch = userService.findByUsername(user.getUsername());
        if (userToSearch.isPresent()) {
            var u = userToSearch.get();
            model.addAttribute("movies", favouritesService.getMoviesByUserId(u.getId()));
            System.out.println(favouritesService.getMoviesByUserId(u.getId()));
            return "favourites";
        } else {
            return "index";
        }
    }

    @PatchMapping("{movie_id}")
    public String add(@AuthenticationPrincipal User authUser, @PathVariable Long movie_id) {
        var optUser = userService.findByUsername(authUser.getUsername());
        if (optUser.isPresent()) {
            var user = optUser.get();
            if (favouritesService.getByUserId(user.getId()).isPresent()) {
                favouritesService.addMovie(favouritesService.getByUserId(user.getId()).get().getId(), movie_id);
                return "redirect:/{movie_id}";
            }
        }
        return "index";
    }

    @DeleteMapping("{movie_id}")
    public String delete(@AuthenticationPrincipal User authUser, @PathVariable Long movie_id) {
        var optUser = userService.findByUsername(authUser.getUsername());
        if (optUser.isPresent()) {
            var user = optUser.get();
            if (favouritesService.getByUserId(user.getId()).isPresent()) {
                favouritesService.deleteMovieById(movie_id, user.getId());
                return "redirect:/{movie_id}";
            }
        }
        return "index";
    }
}
