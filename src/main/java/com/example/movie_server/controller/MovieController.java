package com.example.movie_server.controller;

import com.example.movie_server.model.Actor;
import com.example.movie_server.model.Movie;
import com.example.movie_server.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getAll();
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
    public List<Movie> searchMovies(@PathVariable String title){
        return movieService.searchMovies(title);
    }
}
