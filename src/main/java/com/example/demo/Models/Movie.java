package com.example.demo.Models;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 1, max = 20, message = "movie name length must be between 1 and 20 characters")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @NotNull
    @Length(min = 10, max = 500, message = "movie annotation name length must be between 10 and 500 characters")
    private String annotation;

    @NotNull
    @ColumnDefault(value = "0")
    private Float rating;

    @NotNull
    @ColumnDefault(value = "0")
    private Integer ageRating;

    private String posterPath;

    @Column(name = "year_of_creation")
    private Integer yearOfCreation;

    private Integer duration;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movies_favourites",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "favourites_id", referencedColumnName="id")
    )
    private Set<Favourites> favourites;

    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews;

    public List<String> getGenres() {
        return genres.stream().map(Genre::getGenre).toList();
    }
}
