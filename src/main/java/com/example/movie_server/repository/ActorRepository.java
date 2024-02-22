package com.example.movie_server.repository;

import com.example.movie_server.model.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {
    List<Actor> findActorByFirstName(String firstName);
}
