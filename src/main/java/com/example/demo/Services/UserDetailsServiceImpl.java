package com.example.demo.Services;

import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.UserRepo;
import com.example.demo.Security.UserDetailsMy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("detailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return UserDetailsMy.fromUser(user);
    }
}
