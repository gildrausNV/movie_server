package com.example.movie_server.controller;

import com.example.movie_server.model.Actor;
import com.example.movie_server.model.Genre;
import com.example.movie_server.model.Movie;
import com.example.movie_server.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public Page<Movie> getAllMovie(Pageable pageable){
        return movieService.getAll(pageable);
    }

    @GetMapping("/{movieId}")
    public Movie getMovieById(@PathVariable String movieId){
        return movieService.getMovieById(movieId);
    }

    @GetMapping("/actors/{movieId}")
    public List<Actor> getMovieActors(@PathVariable String movieId) {
        return movieService.getMovieActors(movieId);
    }

    @PostMapping
    public Movie saveMovie(@RequestBody Movie movie){
        return movieService.saveMovie(movie);
    }

    @GetMapping("/isInMyWatchlist/{movieId}")
    public boolean isInWatchlist(@PathVariable String movieId){
        return movieService.isInWatchlist(movieId);
    }

    @PutMapping("/{movieId}")
    public Movie updateMovie(@PathVariable String movieId, @RequestBody Movie movie){
        return movieService.updateMovie(movieId, movie);
    }

    @GetMapping("/search/{title}")
    public List<Movie> searchMoviesByTitle(@PathVariable String title){
        return movieService.searchMoviesByTitle(title);
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> searchMoviesByGenre(@PathVariable String genre){
        Genre genreValue = Genre.valueOf(genre.toUpperCase());
        return movieService.searchMoviesByGenre(genreValue);
    }

}
