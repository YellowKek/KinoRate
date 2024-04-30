package com.example.demo.Repositories;

import com.example.demo.Models.Favourites;
import com.example.demo.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouritesRepo extends JpaRepository<Favourites, Long> {
    Optional<Favourites> findByUserId(Long id);

    Optional<Favourites> findByUser(UserEntity user);

    @Modifying
    @Query(value = "insert into movies_favourites (favourites_id, movie_id) values (:favourites, :movie);", nativeQuery = true)
    int add(@Param("favourites") Long favouritesId, @Param("movie") Long movieId);


    @Modifying
    @Query(value = "delete from movies_favourites where favourites_id = :favouritesId and movie_id = :movieId", nativeQuery = true)
    void deleteMovie(@Param("favouritesId") Long favouritesId, @Param("movieId") Long movieId);

}
