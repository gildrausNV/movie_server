package com.example.movie_server.controller;

import com.example.movie_server.model.User;
import com.example.movie_server.model.Watchlist;
import com.example.movie_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/currentlyLoggedIn")
    public User getCurrentlyLoggedIn(){
        return userService.getCurrentlyLoggedInUser();
    }

    @GetMapping("/watchlist")
    public Watchlist getWatchlist(){
        return userService.getWatchlist();
    }

    @GetMapping("/watchlist/{userId}")
    public Watchlist getWatchlistByUser(@PathVariable String userId){
        return userService.findWatchlistByUserId(userId);
    }

    @PostMapping("/watchlist/{movieId}")
    public Watchlist addToWatchlist(@PathVariable String movieId){
        return userService.addToWatchlist(movieId);
    }

    @DeleteMapping("/watchlist/{movieId}")
    public Watchlist removeFromWatchlist(@PathVariable String movieId){
        return userService.removeFromWatchlist(movieId);
    }
}
