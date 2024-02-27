package com.example.movie_server.service;

import com.example.movie_server.model.Movie;
import com.example.movie_server.model.Review;
import com.example.movie_server.model.User;
import com.example.movie_server.repository.MovieRepository;
import com.example.movie_server.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public List<Review> getMovieReviews(String movieId){
        return reviewRepository.findReviewByMovie_Id(movieId);
    }

    public Review saveReview(Review review, String movieId){
        User currentlyLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movie movie = movieRepository.findById(movieId).orElseThrow(NoSuchElementException::new);
        review.setMovie(movie);
        review.setUser(currentlyLoggedInUser);
        return reviewRepository.save(review);
    }

    public void deleteReview(String reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
