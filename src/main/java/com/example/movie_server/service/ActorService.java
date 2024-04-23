package com.example.movie_server.service;

import com.example.movie_server.model.Actor;
import com.example.movie_server.model.ActorRole;
import com.example.movie_server.model.Movie;
import com.example.movie_server.repository.ActorRepository;
import com.example.movie_server.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public Page<Actor> getAllActors(Pageable pageable){
        return actorRepository.findAll(pageable);
    }

    public Actor getActorById(String actorId) {
        return actorRepository.findById(actorId).orElseThrow(() -> new NoSuchElementException("Actor not found with ID: " + actorId));
    }

    public Actor saveActor(Actor actor) {
        if (actor == null ||
                actor.getFirstName() == null || actor.getFirstName().isEmpty() ||
                actor.getLastName() == null || actor.getLastName().isEmpty() ||
                actor.getDateOfBirth() == null || actor.getDateOfBirth().isEmpty() ||
                actor.getNationality() == null || actor.getNationality().isEmpty() ||
                actor.getImage() == null || actor.getImage().isEmpty() ||
                actor.getPlaceOfBirth() == null || actor.getPlaceOfBirth().isEmpty()) {
            throw new IllegalArgumentException("Actor fields cannot be empty");
        }
        return actorRepository.save(actor);
    }

//    public List<Actor> saveActors(List<Actor> actors) {
//        List<Actor> currentActors = actorRepository.findAll();
//        for (Actor actor: actors){
//            if(!currentActors.contains(actor)){
//                actorRepository.save(actor);
//            }
//        }
//        return actorRepository.findAll();
//    }

    public List<Actor> searchActors(String firstName) {
        return actorRepository.findActorByFirstName(firstName);
    }

    public List<Movie> getActorMovies(String actorId) {
        List<Movie> movies = movieRepository.findAll();

        return movies.stream()
                .filter(movie -> movie.getRoles().stream()
                        .anyMatch(role -> role.getActorId().equals(actorId)))
                .collect(Collectors.toList());
    }

    public Actor updateActor(String actorId, Actor actor) {
        if (actor == null ||
                actor.getFirstName() == null || actor.getFirstName().isEmpty() ||
                actor.getLastName() == null || actor.getLastName().isEmpty() ||
                actor.getDateOfBirth() == null || actor.getDateOfBirth().isEmpty() ||
                actor.getNationality() == null || actor.getNationality().isEmpty() ||
                actor.getImage() == null || actor.getImage().isEmpty() ||
                actor.getPlaceOfBirth() == null || actor.getPlaceOfBirth().isEmpty()) {
            throw new IllegalArgumentException("Actor fields cannot be empty");
        }
        Actor existingActor = actorRepository.findById(actorId).orElseThrow(() -> new NoSuchElementException("Actor not found with ID: " + actorId));

        existingActor.setNationality(actor.getNationality());
        existingActor.setImage(actor.getImage());
        existingActor.setLastName(actor.getLastName());
        existingActor.setFirstName(actor.getFirstName());
        existingActor.setDateOfBirth(actor.getDateOfBirth());
        existingActor.setPlaceOfBirth(actor.getPlaceOfBirth());

        return actorRepository.save(existingActor);
    }
}
