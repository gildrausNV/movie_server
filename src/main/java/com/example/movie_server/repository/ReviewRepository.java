package com.example.movie_server.repository;

import com.example.movie_server.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewByMovie_Id(String movieId);

}
