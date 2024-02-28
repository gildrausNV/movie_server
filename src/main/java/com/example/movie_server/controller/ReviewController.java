package com.example.movie_server.controller;

import com.example.movie_server.model.Review;
import com.example.movie_server.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{movieId}")
    public List<Review> getMovieReviews(@PathVariable String movieId){
        return reviewService.getMovieReviews(movieId);
    }

    @PostMapping("/{movieId}")
    public Review saveReview(@RequestBody Review review, @PathVariable String movieId){
        return reviewService.saveReview(review, movieId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable String reviewId){
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/rating/{movieId}")
    public long getMovieRating(@PathVariable String movieId){
        return reviewService.getMovieRating(movieId);
    }
}
