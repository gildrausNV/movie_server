package com.example.movie_server.service;

import com.example.movie_server.model.Movie;
import com.example.movie_server.model.Review;
import com.example.movie_server.model.User;
import com.example.movie_server.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("Test saveReview()")
    public void saveReviewTest(){
        // Given
        User user = User.builder().id("1").build();
        Review review = Review.builder()
                .id("001")
                .movie(new Movie())
                .user(user)
                .content("Good movie")
                .rating(5)
                .build();

        when(reviewService.saveReview(review)).thenReturn(review);

        // When
        Review savedReview = reviewService.saveReview(review);

        // Then
        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isEqualTo("001");
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    @DisplayName("Test saveReview() with empty fields")
    public void saveReviewWithEmptyFieldsTest(){
        Review review = Review.builder()
                .id("001")
                .movie(null)
                .user(null)
                .content("")
                .rating(7)
                .build();

        assertThrows(IllegalArgumentException.class, () -> reviewService.saveReview(review, ""));
    }

    @Test
    @DisplayName("Test saveReview() with rating less than 0")
    public void saveReviewWithRatingLessThanZero(){
        User user = User.builder().id("1").build();
        Review review = Review.builder()
                .id("001")
                .movie(new Movie())
                .user(user)
                .content("fds")
                .rating(-3)
                .build();

        assertThrows(IllegalArgumentException.class, () -> reviewService.saveReview(review, ""));
    }

    @Test
    @DisplayName("Test findReviewByMovieId()")
    public void findReviewByMovieIdTest(){
        // Given
        Movie movie = Movie.builder().id("001").build();
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
                .id("1")
                .movie(movie)
                .user(new User())
                .content("Good movie")
                .rating(5)
                .build());
        reviews.add(Review.builder()
                .id("2")
                .movie(movie)
                .user(new User())
                .content("Bad movie")
                .rating(5)
                .build());

        when(reviewRepository.findReviewByMovie_Id("001")).thenReturn(reviews);
        // When
        List<Review> result = reviewService.getMovieReviews("001");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(reviews);
        verify(reviewRepository, times(1)).findReviewByMovie_Id("001");
    }
}
