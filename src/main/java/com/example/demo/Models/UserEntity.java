package com.example.demo.Models;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 5, max = 20, message = "username length must be between 5 and 20 characters")
    @NotNull
    private String username;

    @Size(min = 3, max = 100, message = "password length must be between 5 and 100 chars")
    @Column(name = "password")
    @NotEmpty(message = "password must not be empty")
    @NotNull
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Review> leftReviews;
}
