package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favourites {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity user;

    @ManyToMany(mappedBy = "favourites")
    private Set<Movie> movies;

    public Favourites(UserEntity user) {
        this.user = user;
    }
}