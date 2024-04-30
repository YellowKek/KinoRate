package com.example.demo.Services;

import com.example.demo.Models.Favourites;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final FavouritesService favouritesService;

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    public void saveNew(UserEntity user) {
        userRepo.save(user);
        favouritesService.save(new Favourites(user));
    }

    public void validate(UserEntity user, Errors errors) {
        if (findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "Username exists");
        }
    }

    public void save(UserEntity userEntity) {
        userRepo.save(userEntity);
    }

    public void update(UserEntity user, Long id) {
        user.setId(id);
        userRepo.save(user);
    }

    public Optional<UserEntity> getById(Long id) {
        return userRepo.findById(id);
    }

    public boolean matches(String password, String basePassword) {
        return passwordEncoder.matches(password, basePassword);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

}
