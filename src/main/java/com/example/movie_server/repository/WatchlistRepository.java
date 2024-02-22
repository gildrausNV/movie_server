package com.example.movie_server.repository;

import com.example.movie_server.model.Watchlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends MongoRepository<Watchlist, String> {
    Watchlist findWatchlistByUser_Id(String userId);
}
