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
        if(review.getContent() == null || review.getContent().isEmpty()) throw new IllegalArgumentException("Review comment cannot be empty");
        User currentlyLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NoSuchElementException("Movie not found with ID: " + movieId));
        review.setMovie(movie);
        review.setUser(currentlyLoggedInUser);
        return reviewRepository.save(review);
    }

    public Review saveReview(Review review){
//        User currentlyLoggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        review.setUser(currentlyLoggedInUser);
        return reviewRepository.save(review);
    }

    public void deleteReview(String reviewId){
        reviewRepository.deleteById(reviewId);
    }

    public long getMovieRating(String movieId) {
        List<Review> movieReviews = getMovieReviews(movieId);

        if(movieReviews.size() == 0) return 0;

        long rating = 0;

        for(Review review: movieReviews) rating += review.getRating();

        return rating/movieReviews.size();
    }
}
