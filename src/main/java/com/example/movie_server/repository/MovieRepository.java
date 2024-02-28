package com.example.movie_server.repository;

import com.example.movie_server.model.Genre;
import com.example.movie_server.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findMoviesByTitleLike(String title);
    Page<Movie> findMoviesByGenre(Genre genre, Pageable pageable);
}
