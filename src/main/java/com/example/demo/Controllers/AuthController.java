package com.example.demo.Controllers;

import com.example.demo.Models.UserEntity;
import com.example.demo.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @GetMapping("register")
    public String registration(@ModelAttribute("user") UserEntity user) {
        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("user") @Valid UserEntity user, BindingResult bindingResult, HttpServletRequest request) {
        userService.validate(user, bindingResult);
        if (!bindingResult.hasErrors()) {
            user.setPassword(userService.encode(user.getPassword())); // Шифрование пароля
            userService.saveNew(user);

            // Создание аутентификационного токена с именем пользователя и зашифрованным паролем
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // Установка аутентификации в контексте безопасности
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Перенаправление пользователя на главную страницу
            return "redirect:/";
        }

        return "register";
    }


}
