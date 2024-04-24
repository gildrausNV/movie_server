package com.example.movie_server.service;

import com.example.movie_server.model.Movie;
import com.example.movie_server.model.User;
import com.example.movie_server.model.Watchlist;
import com.example.movie_server.repository.MovieRepository;
import com.example.movie_server.repository.UserRepository;
import com.example.movie_server.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WatchlistRepository watchlistRepository;
    private final MovieRepository movieRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(String userId){
        return userRepository.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }

    public Watchlist findWatchlistByUserId(String userId) {
        return watchlistRepository.findWatchlistByUser_Id(userId);
    }

    public Watchlist addToWatchlist(String movieId) {
        User currentlyLoggedInUser = getCurrentlyLoggedInUser();
        Watchlist watchlist = watchlistRepository.findWatchlistByUser_Id(currentlyLoggedInUser.getId());
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NoSuchElementException("Movie not found with ID: " + movieId));
        List<Movie> movies = watchlist.getMovies();

        if(!movies.contains(movie)){
            movies.add(movie);
        }

        watchlist.setMovies(movies);
        return watchlistRepository.save(watchlist);
    }

    public void createWatchlist(User user){
        watchlistRepository.save(Watchlist.builder().user(user).movies(new ArrayList<>()).build());
    }

    public User save(User user) {
        if (user == null ||
                user.getFirstName() == null || user.getFirstName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User fields cannot be empty");
        }
        return userRepository.save(user);
    }


    public User getCurrentlyLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Watchlist removeFromWatchlist(String movieId) {
        User currentlyLoggedInUser = getCurrentlyLoggedInUser();
        Watchlist watchlist = watchlistRepository.findWatchlistByUser_Id(currentlyLoggedInUser.getId());
        Movie movie = movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);
        List<Movie> movies = watchlist.getMovies();
        movies.remove(movie);
        return watchlistRepository.save(watchlist);
    }

    public Watchlist getWatchlist() {
        User currentlyLoggedInUser = getCurrentlyLoggedInUser();
        return watchlistRepository.findWatchlistByUser_Id(currentlyLoggedInUser.getId());
    }
}
