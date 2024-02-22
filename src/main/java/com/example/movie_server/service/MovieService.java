package com.example.movie_server.service;

import com.example.movie_server.model.*;
import com.example.movie_server.repository.ActorRepository;
import com.example.movie_server.repository.MovieRepository;
import com.example.movie_server.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final WatchlistRepository watchlistRepository;

    public List<Movie> getAll(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(String movieId) {
        return movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);
    }

    public List<Actor> getMovieActors(String movieId) {
        List<ActorRole> roles = getMovieById(movieId).getRoles();

        List<Actor> movieActors = new ArrayList<>();

        for(ActorRole ar: roles){
            movieActors.add(actorRepository.findById(ar.getActorId()).orElseThrow(NoSuchElementException::new));
        }

        return movieActors;
    }


    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public boolean isInWatchlist(String movieId) {
        User currentlyLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Watchlist watchlist = watchlistRepository.findWatchlistByUser_Id(currentlyLoggedInUser.getId());
        List<Movie> movies = watchlist.getMovies();
        Movie movie = getMovieById(movieId);
        return movies.contains(movie);
    }

    public Movie updateMovie(String movieId, Movie movie) {
        Movie existingMovie = movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);

        existingMovie.setDescription(movie.getDescription());
        existingMovie.setImage(movie.getImage());
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setRoles(movie.getRoles());

        return movieRepository.save(existingMovie);
    }

    public List<Movie> searchMovies(String title) {
        return movieRepository.findMoviesByTitleLike(title);
    }
}
