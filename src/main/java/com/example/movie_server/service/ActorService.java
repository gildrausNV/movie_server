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

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public Page<Actor> getAllActors(Pageable pageable){
        return actorRepository.findAll(pageable);
    }

    public Actor getActorById(String actorId) {
        return actorRepository.findById(actorId).orElseThrow(NoSuchElementException::new);
    }

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> saveActors(List<Actor> actors) {
        List<Actor> currentActors = actorRepository.findAll();
        for (Actor actor: actors){
            if(!currentActors.contains(actor)){
                actorRepository.save(actor);
            }
        }
        return actorRepository.findAll();
    }

    public List<Actor> searchActors(String firstName) {
        return actorRepository.findActorByFirstName(firstName);
    }

    public List<Movie> getActorMovies(String actorId) {
        List<Movie> movies = movieRepository.findAll();
        List<Movie> actorMovies = new ArrayList<>();

        for(Movie movie: movies){
            List<ActorRole> roles = movie.getRoles();
            for(ActorRole role: roles){
                if(role.getActorId().equals(actorId)){
                    actorMovies.add(movie);
                    break;
                }
            }
        }

        return actorMovies;
    }
}
